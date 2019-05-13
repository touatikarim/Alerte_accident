package com.example.alertaccident.presentation

import android.app.Activity
import android.content.Context
import android.widget.TextView

interface IHomePresenter {
    fun getLocation(activity: Activity, Lat: TextView,area:TextView,Lng:TextView )
    fun setMainViewContext(context: Context)
}