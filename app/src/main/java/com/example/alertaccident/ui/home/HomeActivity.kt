package com.example.alertaccident.ui.home

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.alertaccident.R
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.Connexion
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.material.navigation.NavigationView

import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.fragment_settings.*


class HomeActivity : AppCompatActivity() {


    lateinit var mGoogleApiClient: GoogleApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setSupportActionBar(toolbar)
        val navController = Navigation.findNavController(this,R.id.my_nav_user_fragment)
        val sp = UserManager.getSharedPref(this)
        val mail=sp.getString("USER_EMAIL","")
        val name=sp.getString("USER_NAME","")
        setupBottomNavMenu(navController)
        setupSideNavigationMenu(navController,mail,name)
        setupActionBar(navController)
        setsize(logout)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        mGoogleApiClient.connect()
        logout.setOnClickListener {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                object : ResultCallback<Status> {
                    override fun onResult(status: Status) {

                        val intent = Intent(applicationContext, Connexion::class.java)
                       startActivity(intent)
                        finish()

                    }
                })
        }



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
        val navController=Navigation.findNavController(this,R.id.my_nav_user_fragment)
        val navigated=NavigationUI.onNavDestinationSelected(item!!,navController)
        return navigated || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
         return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.my_nav_user_fragment),drawer_layout)
    }

    private fun setsize (textView: TextView){
        val density= getResources().getDisplayMetrics().density
        val width=Math.round(30 * density)
        val height = Math.round(24 * density)
        val drawable= ResourcesCompat.getDrawable(getResources(), R.drawable.logout, null);
        drawable!!.setBounds(0,0,width,height)
        textView.setCompoundDrawables(drawable, null, null, null);


    }
}
