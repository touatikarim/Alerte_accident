package com.example.alertaccident.presentation

import android.app.Activity
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Looper
import com.example.alertaccident.helper.GPSUtils
import com.example.alertaccident.retrofit.UserManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class MapPresenterImpl:IMapPresenter {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var context: Context
    override fun getLocation(activity:Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (GPSUtils.checkLocationPermission(activity, context)) {
                GPSUtils.buildLocationRequest()
                GPSUtils.buildLocationCallback()
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
                fusedLocationProviderClient.requestLocationUpdates(
                    GPSUtils.locationRequest,
                    GPSUtils.locationCallback, Looper.myLooper())
                fusedLocationProviderClient.lastLocation
                    .addOnSuccessListener { location ->
                        val latitude = location?.latitude
                        val longitud = location?.longitude
                        if (latitude != null && longitud != null) {
                            val geocoder = Geocoder(context)
                            val adress = geocoder.getFromLocation(latitude, longitud, 10)
                            val country = adress.get(0).countryName
                            val city= GPSUtils.getCity(latitude,longitud,context)
                            val sub = GPSUtils.getarea(latitude,longitud,context)

                            UserManager.saveplace(country, city, sub, context)
                        }
                        UserManager.saveposition(latitude.toString(), longitud.toString(), context)

                    }
            }
        }

    }
    override fun setMainViewContext(context: Context) {
        this.context=context
    }
}