package com.example.alertaccident.presentation

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import com.example.alertaccident.helper.*
import com.example.alertaccident.model.GoogleApiResponse
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.retrofit.UserManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapPresenterImpl:IMapPresenter {

    private var mClusterManager: ClusterManager<MarkerClusterItem>?=null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var context: Context
    override fun getLocation(activity:Activity,mMap: GoogleMap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (GPSUtils.checkLocationPermission(activity, context)) {
//                GPSUtils.buildLocationRequest()
//                GPSUtils.buildLocationCallback()
               // val mapSettings=mMap.uiSettings
               // mapSettings?.isMyLocationButtonEnabled = true
                mMap.setMyLocationEnabled(true)
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
//                            val user_markerOptions=MarkerOptions()
//                                .position(LatLng(latitude.toDouble(),longitud.toDouble()))
//                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                                .title("You're here")
//                            val user_markerOptions=CircleOptions()
//                                .center(LatLng(latitude,longitud))
//                                .radius(100.0)
//                                .fillColor(Color.BLUE)
//
//                            mMap.addCircle(user_markerOptions)

                            //mMap.addMarker(user_markerOptions)
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude.toDouble(), longitud.toDouble())))
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(17f))
                            UserManager.saveplace(country, city, sub, context)

                        }
                        UserManager.saveposition(latitude.toString(), longitud.toString(), context)

                    }

            }
        }

    }
    override fun getNearbyPlaces(typePlace: String, context: Context,mMap:GoogleMap,latitude:String,longitude:String) {
        mMap.clear()
        val url=getUrl(latitude,longitude,typePlace)
        mClusterManager=ClusterManager(context,mMap)
        mMap.setOnCameraIdleListener(mClusterManager)
        val renderer=CustomClusterRenderer(context,mMap, mClusterManager!!)
        mClusterManager!!.renderer=renderer
        RetrofitManager.getInstance(Constants.placeurl).service!!.nearbyPlaces(url).enqueue(object : Callback<GoogleApiResponse> {
            override fun onResponse(call: Call<GoogleApiResponse>, response: Response<GoogleApiResponse>) {
                //var currentPlace = response.body()
                if (response.isSuccessful) {
                    for (i in 0 until response.body()!!.results!!.size) {
                        val googlePlace = response.body()!!.results!![i]
                        val markerOptions = MarkerOptions()
                        val lat = googlePlace.geometry!!.location.lat
                        val lng = googlePlace.geometry!!.location.lng
                        val latLng = LatLng(lat, lng)
                        val name = googlePlace.name
                        mClusterManager!!.addItem(MarkerClusterItem(latLng,name))



                        mMap.animateCamera(CameraUpdateFactory.zoomTo(17f))
                    }
                    mClusterManager!!.cluster()
                }
            }
            override fun onFailure(call: Call<GoogleApiResponse>, t: Throwable) {
                Log.d("error",t.message)
            }
        })
    }

    private fun getUrl(latitude: String, longitude: String, typePlace: String): String {
        val googlePlaceUrl=StringBuilder(Constants.placeurl)
        googlePlaceUrl.append("json?")
        googlePlaceUrl.append("location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=10000")
        googlePlaceUrl.append("&type=$typePlace")
        googlePlaceUrl.append("&key=AIzaSyCyuJmNnafNVdx390P07u5X6JwNiZYySnI")
        return googlePlaceUrl.toString()


    }

    override fun setMainViewContext(context: Context) {
        this.context=context
    }
}