package com.example.alertaccident.ui.home


import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.telephony.SmsManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.alertaccident.Local.ContactDataSource
import com.example.alertaccident.Local.ContactDatabase
import com.example.alertaccident.R
import com.example.alertaccident.Service.CrashDetectionService
import com.example.alertaccident.database.ContactRepository
import com.example.alertaccident.helper.GPSUtils
import com.example.alertaccident.helper.PermissionUtils
import com.example.alertaccident.model.AlertModel
import com.example.alertaccident.model.Contact
import com.example.alertaccident.model.User
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.Connexion
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import es.dmoral.toasty.Toasty
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


import kotlinx.android.synthetic.main.activity_user.*
import java.lang.Boolean.FALSE
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() {

    private var compositeDisposable: CompositeDisposable? = null
    lateinit var contactList: List<Contact>
    lateinit var contactRepository: ContactRepository
    lateinit var mGoogleApiClient: GoogleApiClient
    private var CrashStatus:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        if (!GPSUtils.isLocationEnabled(this))
            GPSUtils.showAlert(this,this)




        setSupportActionBar(toolbar)

        startService(Intent(this@HomeActivity,CrashDetectionService::class.java))

        val navController = Navigation.findNavController(this,R.id.my_nav_user_fragment)
        val sp = UserManager.getSharedPref(this)
        val mail=sp.getString("USER_EMAIL","")
        val name=sp.getString("USER_NAME","")
        val token_google=sp.getString("GOOGLE_SIGNED_IN","")
        val id=sp.getString("USER_ID","")

        setupBottomNavMenu(navController)
        setupSideNavigationMenu(navController,mail,name)
        setupActionBar(navController)
        setsize(logoutfb)
        setsize(logoutgoogle)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        mGoogleApiClient.connect()
        if(AccessToken.getCurrentAccessToken() != null){
            logoutfb.setVisibility(View.VISIBLE)
            logoutfb.setOnClickListener {
                AccessToken.setCurrentAccessToken(null)
                LoginManager.getInstance().logOut()
                UserManager.clearSharedPref(this)
                cleartoken(id)
                val options=ActivityOptions.makeCustomAnimation(this,R.anim.slide_in_left,R.anim.slide_out_right)
                val intent = Intent(applicationContext, Connexion::class.java)
                load()
                stopService(Intent(this@HomeActivity,CrashDetectionService::class.java))
                Handler().postDelayed({startActivity(intent,options.toBundle());finish()},1500)


            }

        }
        else if(token_google!=null)  {
            logoutgoogle.setVisibility(View.VISIBLE)
            logoutgoogle.setOnClickListener {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    object : ResultCallback<Status> {
                        override fun onResult(status: Status) {
                            val options=ActivityOptions.makeCustomAnimation(this@HomeActivity,R.anim.slide_in_left,R.anim.slide_out_right)
                            val intent = Intent(applicationContext, Connexion::class.java)
                            UserManager.clearSharedPref(this@HomeActivity)
                            cleartoken(id)
                            load()
                            stopService(Intent(this@HomeActivity,CrashDetectionService::class.java))
                            Handler().postDelayed({startActivity(intent,options.toBundle());finish()},1500)

                        }
                    })
            }
       }
        else {
            logout.setVisibility(View.VISIBLE)
            logout.setOnClickListener {
                val options=ActivityOptions.makeCustomAnimation(this@HomeActivity,R.anim.slide_in_left,R.anim.slide_out_right)
                val intent = Intent(applicationContext, Connexion::class.java)
                UserManager.clearSharedPref(this@HomeActivity)
                cleartoken(id)
                load()
                stopService(Intent(this@HomeActivity,CrashDetectionService::class.java))
                Handler().postDelayed({startActivity(intent,options.toBundle());finish()},1500)
            }

        }

        //val CrashStatus=sp.getBoolean("CRASH_ACCURED",true)
         CrashStatus=UserManager.getAccidentDetectionService(this)
        if (CrashStatus) {
            Toast.makeText(this, "CRASHED", Toast.LENGTH_LONG).show()
            val builder = AlertDialog.Builder(this)
            builder.setMessage("A collision was detected, an SMS and alert will be sent in 10 seconds")
            builder.setPositiveButton(
                "OK"
            ) { _, _ -> sendCrashAlert()
                sendSms()
               // UserManager.detectCrash(this, FALSE)


            }
                .setNegativeButton("No, Don't send ") { dialog, _ ->
                    dialog.dismiss()
                   // UserManager.detectCrash(this, FALSE)
                }
            builder.show()
        }

        val contactDatabase = ContactDatabase.getInstance(this)
        contactRepository = ContactRepository.getInstance(ContactDataSource.getInstance(contactDatabase.contactDAO()))
        compositeDisposable= CompositeDisposable()
        val disposable = Observable.create(ObservableOnSubscribe<Any>{ e->
            contactList=contactRepository.getContactById(id)
            for(contact:Contact in contactList){
                Log.d("contact",contact.Phone_Number)
            }
            e.onComplete()
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({}, { throwable-> Log.d("error",throwable.message) }, { })
        compositeDisposable!!.add(disposable)

    }

    private fun sendSms(){
        if(PermissionUtils.checkSmsPermission(this)){
        val sms=SmsManager.getDefault()
        for (contact:Contact in contactList){
            sms.sendTextMessage(contact.Phone_Number,null,"Help! I've met with an accident at skdjqlkjsdqlk",null,null)
        }
            }
        else (PermissionUtils.requestSendSmsPermission(this))
    }








    private fun cleartoken(user_id:String){
        val database: DatabaseReference = FirebaseDatabase.getInstance().reference
        database.child("Tokens").child(user_id).removeValue()
    }

    private fun load() {
        val progressBar = logout_bar
        progressBar.setVisibility(View.VISIBLE)
        Handler().postDelayed({ progressBar.setVisibility(View.GONE) }, 1500)
    }
    private fun setupBottomNavMenu(navController: NavController){
        bottom_nav?.let {
            NavigationUI.setupWithNavController(it,navController)
        }
    }

    private fun setupSideNavigationMenu(navController: NavController,email:String,name:String){
        nav_view?.let {
            NavigationUI.setupWithNavController(it,navController)
            val navigationView= findViewById<NavigationView>(R.id.nav_view)
            val headerLayout =navigationView.getHeaderView(0)
            val email_id=headerLayout.findViewById<TextView>(R.id.user_email_id)
            email_id.setText(email)
            val name_id=headerLayout.findViewById<TextView>(R.id.user_name_id)
            name_id.setText(name)

        }

    }


   private fun setupActionBar(navController: NavController){
       NavigationUI.setupActionBarWithNavController(this,navController,drawer_layout)
   }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val navController = Navigation.findNavController(this, R.id.my_nav_user_fragment)
        val navigated = NavigationUI.onNavDestinationSelected(item!!, navController)
        return navigated || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
         return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.my_nav_user_fragment),drawer_layout)
    }

    private fun setsize (textView: TextView){
        val density= getResources().getDisplayMetrics().density
        val width=Math.round(30 * density)
        val height = Math.round(24 * density)
        val drawable= ResourcesCompat.getDrawable(getResources(), R.drawable.logout, null)
        drawable!!.setBounds(0,0,width,height)
        textView.setCompoundDrawables(drawable, null, null, null)


    }

    fun sendCrashAlert(){
        val sp = UserManager.getSharedPref(this)
        val email = sp.getString("USER_EMAIL", "")
        val latitude=sp.getString("USER_LAT","")
        val longitude=sp.getString("USER_LNG","")
        val country=sp.getString("USER_COUNTRY","")
        val city=sp.getString("USER_CITY","")
        val area=sp.getString("USER_AREA","")
        val user_id = sp.getString("USER_ID", "")
        val current = Date()
        val formatter = SimpleDateFormat("MMM/dd/yyyy-HH:mma", Locale.getDefault())
        val date = formatter.format(current)
        val location=country+","+city+","+area
        val ref = FirebaseDatabase.getInstance().getReference("Alerts")
        val alert_id = ref.push().key
        UserManager.savecurrentAlertId(this,alert_id)
        val alert = AlertModel(alert_id, user_id, "Road Accident", "Hospital", email, "1",latitude,longitude,"","",date,location)
        ref.child(alert_id!!).setValue(alert).addOnCompleteListener {
            Toasty.success(this,"Alert Sent", Toast.LENGTH_SHORT).show()
        }
    }






}
