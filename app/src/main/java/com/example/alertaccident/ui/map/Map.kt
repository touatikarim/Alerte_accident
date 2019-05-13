package com.example.alertaccident.ui.map

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import com.example.alertaccident.R
import com.example.alertaccident.helper.GPSUtils
import com.example.alertaccident.helper.GPSUtils.buildLocationCallback
import com.example.alertaccident.helper.GPSUtils.buildLocationRequest
import com.example.alertaccident.helper.GPSUtils.checkLocationPermission
import com.example.alertaccident.helper.GPSUtils.locationCallback
import com.example.alertaccident.helper.GPSUtils.locationRequest
import com.example.alertaccident.presentation.IMapPresenter
import com.example.alertaccident.presentation.MapPresenterImpl
import com.example.alertaccident.retrofit.UserManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class Map : Fragment() {


    lateinit var mappresenter:IMapPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mappresenter=MapPresenterImpl()
        mappresenter.setMainViewContext(activity!!.baseContext)
        val mapFragment = this.childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync{
            it.clear()
            mappresenter.getLocation(activity!!)
            val sp = UserManager.getSharedPref(activity!!.baseContext)
        val latitude=sp.getString("USER_LAT","")
        val longitude=sp.getString("USER_LNG","")
        val alert_lat=sp.getString("ALERT_LAT","")
        val alert_lng=sp.getString("ALERT_LNG","")
            Log.d("pos",alert_lng)
        val user_markerOptions=MarkerOptions()
            .position(LatLng(latitude.toDouble(),longitude.toDouble()))
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            .title("You're here")

        it?.addMarker(user_markerOptions)
            it?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude.toDouble(), longitude.toDouble())))
            it?.animateCamera(CameraUpdateFactory.zoomTo(12f))


            if(!alert_lat.isEmpty() && !alert_lng.isEmpty() ){
                val alert_markerOptions=MarkerOptions()
                    .position(LatLng(alert_lat.toDouble(),alert_lng.toDouble()))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .title("Alert")
                it?.addMarker(alert_markerOptions)
                it?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(alert_lat.toDouble(), alert_lng.toDouble())))
                it?.animateCamera(CameraUpdateFactory.zoomTo(12f))
                UserManager.clearalertpos(activity!!.baseContext)
            }
        }

    }
}
