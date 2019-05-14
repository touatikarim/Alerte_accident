package com.example.alertaccident.presentation

import android.app.Activity
import android.content.Context
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Looper
import android.view.View
import com.example.alertaccident.R
import com.example.alertaccident.helper.GPSUtils
import com.example.alertaccident.model.GuestAlertModel
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.guest.GuestView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class GuestPresenterImpl(internal var guestview:GuestView):IGuestPresenter {

    lateinit var context: Context
    lateinit var fusedLocationClient: FusedLocationProviderClient
    private var url:String?=null
//    private var latitude:String?=null
//    private var longitude:String?=null
//    private var country:String?=null
//    private var city:String?=null
//    private var area:String?=null
    override fun saveAlert(desc: String,service: String, victims: String,activity: Activity) {
        val sp = UserManager.getSharedPref(context)
        val latitude=sp.getString("USER_LAT","")
        val longitude=sp.getString("USER_LNG","")
        val country=sp.getString("USER_COUNTRY","")
        val city=sp.getString("USER_CITY","")
        val area=sp.getString("USER_AREA","")
        val current = Date()
        val formatter = SimpleDateFormat("MMM/dd/yyyy-HH:mma", Locale.getDefault())
        val date = formatter.format(current)
        val location=country+","+city+","+area
        guestview.load_alert(View.VISIBLE)
        val ref = FirebaseDatabase.getInstance().getReference("Alerts")
        val alert_id = ref.push().key
        val alert = GuestAlertModel(alert_id,desc,service,victims,latitude,longitude,date,location,url)
        ref.child(alert_id!!).setValue(alert).addOnCompleteListener {
            guestview.load_alert(View.GONE)
            guestview.onSuccess(context.getString(R.string.send_alert))
            guestview.navigate()
        }
    }

    override fun setMainViewContext(context: Context) {
     this.context=context
    }

    override fun sendImage(path: String) {
        val storage_ref = FirebaseStorage.getInstance().getReference()
        val file= Uri.fromFile(File(path))
        val image=storage_ref.child("accident/${file.lastPathSegment}")
        val uploadtask=image.putFile(file)
        uploadtask.addOnSuccessListener {
            image.getDownloadUrl().addOnSuccessListener {
                    Uri->url=Uri.toString()
                guestview.load_image(View.GONE)
                guestview.onSuccess("Picture stored successfully")

            }
        }

    }
    override fun getLocation(activity: Activity) {
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
                        val lat = location?.latitude
                        val long = location?.longitude
                        if (lat != null && long != null) {

                            val geocoder = Geocoder(context)
                            val adress = geocoder.getFromLocation(lat, long, 10)
                             val country = adress.get(0).countryName
                            val  city= GPSUtils.getCity(lat,long,context)
                             val sub = GPSUtils.getarea(lat,long,context)
                            UserManager.saveplace(country, city, sub, context)
                        }
                        UserManager.saveposition(lat.toString(),long.toString(),context)
                    }
            }
        } else {
            GPSUtils.buildLocationRequest()
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
            fusedLocationClient.requestLocationUpdates(GPSUtils.locationRequest, GPSUtils.locationCallback, Looper.myLooper())
        }
    }
}