package com.example.alertaccident.ui.map

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.alertaccident.R
import com.example.alertaccident.retrofit.UserManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class Map : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    lateinit var mCurrentMarker: Marker
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap= googleMap!!
//        mMap.uiSettings.isZoomControlsEnabled = true
//        val sp = UserManager.getSharedPref(activity!!.baseContext)
//        val latitude=sp.getString("USER_LAT","")
//        val longitude=sp.getString("USER_LNG","")
//        val markerOptions=MarkerOptions()
//            .position(LatLng(latitude.toDouble(),longitude.toDouble()))
//            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
//
//        mCurrentMarker=mMap.addMarker(markerOptions)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = activity!!.supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }
}
