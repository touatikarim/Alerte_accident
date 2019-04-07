package com.example.alertaccident.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation

import androidx.navigation.ui.setupWithNavController
import com.example.alertaccident.R

import kotlinx.android.synthetic.main.activity_user.*

class HomeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
       // val navController = Navigation.findNavController(this, R.id.home3)
        //bottom_navigation.setupWithNavController(navController)

    }


}
