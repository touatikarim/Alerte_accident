package com.example.alertaccident.ui





import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alertaccident.R
import com.example.alertaccident.helper.GPSUtils


class Connexion : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!GPSUtils.isLocationEnabled(this))
         GPSUtils.showAlert(this,this)

    }



//    override fun onBackPressed() {
//        tellFragments()
//        super.onBackPressed()
//    }
//
//    private fun tellFragments() {
//        val fragments = supportFragmentManager.fragments
//        for (f in fragments) {
//            if (f != null && f is SignIn)
//                f.onBackPressed()
//        }
//    }

}

