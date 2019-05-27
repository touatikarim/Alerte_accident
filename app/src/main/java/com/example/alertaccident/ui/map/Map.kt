package com.example.alertaccident.ui.map

import `in`.galaxyofandroid.spinerdialog.OnSpinerItemClick
import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
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
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants

import com.example.alertaccident.presentation.IMapPresenter
import com.example.alertaccident.presentation.MapPresenterImpl
import com.example.alertaccident.retrofit.UserManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*


class Map : Fragment() {



    lateinit var mappresenter:IMapPresenter
    var spinnerdialog: SpinnerDialog?=null



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
        spinnerdialog= SpinnerDialog(activity,Constants.list_of_items,"Select Service",R.style.DialogAnimations_SmileWindow)
        btnShow.setOnClickListener {
            spinnerdialog!!.showSpinerDialog()
        }
        val mapFragment = this.childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
       mapFragment?.getMapAsync{
            it.clear()
            mappresenter.getLocation(activity!!,it)
           val sp = UserManager.getSharedPref(activity!!.baseContext)
        val latitude=sp.getString("USER_LAT","")
        val longitude=sp.getString("USER_LNG","")
       val alert_lat=sp.getString("ALERT_LAT","")
        val alert_lng=sp.getString("ALERT_LNG","")


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
           spinnerdialog!!.bindOnSpinerListener { item, position ->
               mappresenter.getNearbyPlaces(Constants.list_of_items[position],activity!!.baseContext,it,latitude,longitude)
               mappresenter.getLocation(activity!!,it)
           }
        }

    }
}
