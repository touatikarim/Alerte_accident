package com.example.alertaccident.presentation

import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.app.Activity
import android.content.Context
import android.widget.TextView
import nl.dionsegijn.steppertouch.StepperTouch


interface IcreateAlertPresenter {
     fun saveAlert(desc:String,user_id:String,service:String,victims:String)
    fun setMainViewContext(context: Context)
    fun setstepper(min:Int,max:Int,stepperTouch: StepperTouch,victims:TextView)
    fun setspinner(service:TextView,activity: Activity)
    fun OncreateAlert(desc: String,service:String,victims:String)

}
