package com.example.alertaccident.Service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK

import android.widget.Toast

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.home.HomeActivity
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE


class CrashDetectionService:Service(),CrashListener.OnCrashListener {

    private var mShaker: CrashListener? = null
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    var check: Int = 0

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {

        super.onCreate()
        this.mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        this.mAccelerometer = this.mSensorManager!!.getDefaultSensor(1)
        mShaker = CrashListener(this)
        mShaker!!.setOnCrashListener(this)
        //Toast.makeText(this@CrashDetectionService, "Service is created!", Toast.LENGTH_LONG).show()
        Log.d(packageName, "Created the Service!")
        check = 1
    }


    override fun onShake() {
        if (check == 1) {

            val i = Intent()
            UserManager.detectCrash(this, TRUE)
            i.setClass(this, HomeActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)

        }

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        check = 0
        Log.d(packageName, "Service Destroyed.")
    }

}