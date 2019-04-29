package com.example.alertaccident.helper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.alertaccident.presentation.LoginPresenterImpl
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

object GPSUtils {
    lateinit var locationCallback: LocationCallback
    private lateinit var mLastLocation: Location
    lateinit var locationRequest: LocationRequest
    private const val MY_PERMISSION_CODE: Int = Constants.permission_code


    fun buildLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                mLastLocation = p0!!.lastLocation
            }
        }
    }

     fun checkLocationPermission(activity: Activity,context:Context): Boolean {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSION_CODE
                )
            else
                ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                   MY_PERMISSION_CODE
                )
            return false
        } else
            return true
    }
    fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = Constants.request_interval
        locationRequest.fastestInterval = Constants.request_fastest_interval
        locationRequest.smallestDisplacement = Constants.request_smallestDisplacement


    }

}