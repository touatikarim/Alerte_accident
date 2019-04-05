package com.example.alertaccident.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.alertaccident.R
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val navController = findNavController(this,R.id.my_nav_user_fragment)
        // Set up ActionBar
        setSupportActionBar(toolbar)
        toolbar.setTitle("AlertAccident")
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout )
        // Set up navigation menu
        navigationView.setupWithNavController(navController)
    }
}
