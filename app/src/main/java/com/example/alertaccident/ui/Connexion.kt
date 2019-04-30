package com.example.alertaccident.ui




import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.alertaccident.R
import com.example.alertaccident.helper.GPSUtils
import com.example.alertaccident.ui.login.SignIn


class Connexion : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!GPSUtils.isLocationEnabled(this))
         GPSUtils.showAlert(this,this)

    }



    override fun onBackPressed() {
        tellFragments()
        super.onBackPressed()
    }

    private fun tellFragments() {
        val fragments = supportFragmentManager.fragments
        for (f in fragments) {
            if (f != null && f is SignIn)
                f.onBackPressed()
        }
    }

}

