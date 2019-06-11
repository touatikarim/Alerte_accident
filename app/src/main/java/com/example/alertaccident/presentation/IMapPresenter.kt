package com.example.alertaccident.presentation

import android.app.Activity
import android.content.Context
import android.location.Location
import com.example.alertaccident.ui.map.Map
import com.google.android.gms.maps.GoogleMap

interface IMapPresenter {
    fun getLocation(activity: Activity,mMap: GoogleMap)
    fun getNearbyPlaces(typePlace:String,context: Context,mMap:GoogleMap,latitude:String,longitude:String)
    fun setMainViewContext(context: Context)

}