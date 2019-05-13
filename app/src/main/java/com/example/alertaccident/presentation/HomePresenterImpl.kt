package com.example.alertaccident.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Looper
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.GPSUtils
import com.example.alertaccident.retrofit.UserManager
import com.google.android.gms.location.*

class HomePresenterImpl:IHomePresenter {
    lateinit var context: Context
    lateinit var fusedLocationClient: FusedLocationProviderClient
    @SuppressLint("SetTextI18n")
    override fun getLocation(activity: Activity, Lat: TextView, area: TextView, Lng:TextView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (GPSUtils.checkLocationPermission(activity,context)) {
                GPSUtils.buildLocationRequest()
                GPSUtils.buildLocationCallback()
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
                fusedLocationClient.requestLocationUpdates(
                    GPSUtils.locationRequest,
                    GPSUtils.locationCallback, Looper.myLooper())
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        val latitude = location?.latitude
                        val longitud = location?.longitude
                        if (latitude != null && longitud != null) {
                            val geocoder = Geocoder(context)
                            val adress = geocoder.getFromLocation(latitude, longitud, 10)
                            val country = adress.get(0).countryName
                            val city=GPSUtils.getCity(latitude,longitud,context)
                            val sub = GPSUtils.getarea(latitude,longitud,context)
                            area.text= "$country,$city,\n$sub"
                            Lat.text="LAT:"+latitude.toString()
                            Lng.text="LNG:"+longitud.toString()

                            UserManager.saveplace(country, city, sub, context)
                        }
                        UserManager.saveposition(latitude.toString(),longitud.toString(),context)
                    }
            }
        } else {
            GPSUtils.buildLocationRequest()
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
            fusedLocationClient.requestLocationUpdates(GPSUtils.locationRequest, GPSUtils.locationCallback, Looper.myLooper())
        }
    }


    override fun setMainViewContext(context: Context) {
        this.context=context
    }


}


