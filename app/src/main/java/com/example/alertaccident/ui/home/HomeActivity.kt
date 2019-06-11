package com.example.alertaccident.ui.home


import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.alertaccident.R
import com.example.alertaccident.helper.GPSUtils
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


import kotlinx.android.synthetic.main.activity_user.*



class HomeActivity : AppCompatActivity(),SensorEventListener {


    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var sensorManager: SensorManager
    lateinit var accelerometre:Sensor
    private val TIME_THRESHOLD=75
    private var mLastTime:Long=0
    private var mLastX=-1.0f
    private var mLastY=-1.0f
    private var  mLastZ=-1.0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        if (!GPSUtils.isLocationEnabled(this))
            GPSUtils.showAlert(this,this)
        setSupportActionBar(toolbar)
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
        Log.d("TAG","Initialiazing Sensor Services")
        sensorManager=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometre=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        Log.d("Start","Registred accelerometer listener")
        sensorManager.registerListener(this,accelerometre,SensorManager.SENSOR_DELAY_NORMAL)

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
                Handler().postDelayed({startActivity(intent,options.toBundle());finish()},1500)
            }

        }



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
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        //Log.d("Values","onSensorChanged: X=" + p0!!.values[0]+ "Y=" + p0.values[1] + "Z=" + p0.values[2])
        getAccelerometre(p0)

    }


    private fun getAccelerometre(event:SensorEvent?){
        val now = System.currentTimeMillis()
        val values=event!!.values
        if ((now - mLastTime) > TIME_THRESHOLD){
            val diff=now-mLastTime
            val speed=Math.abs(values[0] + values[1] + values[2] - mLastX - mLastY - mLastZ) * (diff * 10000)
            Log.d("SPEED",speed.toString())
        }
       mLastTime=now
         mLastX = values[0]
         mLastY = values[1]
         mLastZ = values[2]


      //val  accelationSquareRoot = (x*y+y*y+z*z)/(SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH)
        //val actualTime= event.timestamp
        //Log.d("accelattion",accelationSquareRoot.toString())

    }





}
