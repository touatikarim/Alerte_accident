package com.example.alertaccident.presentation

import android.app.Activity
import android.content.Context
import android.location.Location
import com.google.android.gms.maps.GoogleMap

interface IMapPresenter {
    fun getLocation(activity: Activity)
    fun setMainViewContext(context: Context)
}