package com.example.alertaccident.presentation

import android.app.Activity
import android.content.Context
import android.widget.TextView
import nl.dionsegijn.steppertouch.StepperTouch

interface IGuestPresenter {
    fun saveAlert(desc:String,service:String,victims:String,activity:Activity)
    fun setMainViewContext(context: Context)
    fun sendImage(path:String)
    fun getLocation(activity: Activity)

}