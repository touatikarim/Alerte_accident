package com.example.alertaccident.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.alertaccident.R
import com.example.alertaccident.retrofit.UserManager
import com.google.android.material.navigation.NavigationView

import kotlinx.android.synthetic.main.activity_user.*


class HomeActivity : AppCompatActivity() {



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
}
