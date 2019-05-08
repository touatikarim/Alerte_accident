package com.example.alertaccident.helper

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.alertaccident.R
import com.example.alertaccident.presentation.LoginPresenterImpl
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

object GPSUtils {
    lateinit var locationCallback: LocationCallback
    private lateinit var mLastLocation: Location
    lateinit var locationRequest: LocationRequest
    private const val MY_PERMISSION_CODE: Int = Constants.permission_code
    private var locationManager: LocationManager? = null

    fun buildLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                mLastLocation = p0!!.lastLocation
            }
        }
    }

    fun showAlert(activity: Activity, context: Context) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(context.getString(R.string.location))
            .setMessage(context.getString(R.string.Gpsmsg))
            .setPositiveButton(context.getString(R.string.pos_settings)) { paramDialogInterface, paramInt ->
                val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(myIntent)
            }
            .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> activity.finish() }
        dialog.show()
    }

    fun isLocationEnabled(context: Context): Boolean {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    fun checkLocationPermission(activity: Activity, context: Context): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
                ActivityCompat.requestPermissions(
                    activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSION_CODE
                )
            else
                ActivityCompat.requestPermissions(
                    activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
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

    fun getCity(latitude: Double, longitude: Double, context: Context): String {

        val geocoder = Geocoder(context)
        val adress = geocoder.getFromLocation(latitude, longitude, 10)

        if (adress != null && adress.size > 0) {
            for (adr in adress) {
                if (adr.getLocality() != null && adr.getLocality().length > 0) {
                    val city = adr.locality
                    return city
                }
            }
        }
        return "Not available"
    }

    fun getarea(latitude: Double, longitude: Double, context: Context): String {

        val geocoder = Geocoder(context)
        val adress = geocoder.getFromLocation(latitude, longitude, 10)
        if (adress != null && adress.size > 0) {
            for (adr in adress) {

                if (adr.subAdminArea != null && adr.subAdminArea.length > 0) {
                    val area = adr.subAdminArea
                    return area
                }
            }
        }
        return "Not available"
    }
}
