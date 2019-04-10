package com.example.alertaccident.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.navArgs
import androidx.navigation.ui.NavigationUI

import androidx.navigation.ui.setupWithNavController
import com.example.alertaccident.R

import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.nav_header.*

class HomeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setSupportActionBar(toolbar)
        val navController = Navigation.findNavController(this,R.id.my_nav_user_fragment)
        setupBottomNavMenu(navController)
        setupSideNavigationMenu(navController)
        setupActionBar(navController)
        //getargs()


    }

    private fun setupBottomNavMenu(navController: NavController){
        bottom_nav?.let {
            NavigationUI.setupWithNavController(it,navController)
        }
    }

    private fun setupSideNavigationMenu(navController: NavController){
        nav_view?.let {
            NavigationUI.setupWithNavController(it,navController)
        }

    }

   private fun setupActionBar(navController: NavController){
       NavigationUI.setupActionBarWithNavController(this,navController,drawer_layout)
   }

//    private fun getargs(){
//        val args=HomeActivityArgs.fromBundle(intent.extras).usermail
//        user_email_id.text=args
//    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val navController=Navigation.findNavController(this,R.id.my_nav_user_fragment)
        val navigated=NavigationUI.onNavDestinationSelected(item!!,navController)
        return navigated || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
         return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.my_nav_user_fragment),drawer_layout)
    }
}
