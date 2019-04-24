package com.example.alertaccident.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.alertaccident.R
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.Connexion
import com.example.alertaccident.ui.home.HomeActivity
import com.facebook.AccessToken


class Splash : AppCompatActivity() {


    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000 //3 seconds

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            val intent = Intent(applicationContext, Connexion::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sp = UserManager.getSharedPref(this)
        val token_google = sp.getString("GOOGLE_SIGNED_IN", "")
        val token_login = sp.getString("SIGN_TOKEN", "")

        if (token_google!="" || token_login!="" || AccessToken.getCurrentAccessToken() != null )
        {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
        setContentView(R.layout.activity_splash)
        val txt: TextView = findViewById(R.id.name)
        val fade: Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in)
        txt.startAnimation(fade)
        val img: ImageView = findViewById(R.id.imageView)
        img.startAnimation(fade)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
        }

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }


}
