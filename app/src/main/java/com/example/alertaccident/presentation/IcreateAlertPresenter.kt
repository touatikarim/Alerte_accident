package com.example.alertaccident.presentation


import android.app.Activity
import android.content.Context
import android.widget.TextView
import com.example.alertaccident.model.Contact
import nl.dionsegijn.steppertouch.StepperTouch



interface IcreateAlertPresenter {
     fun saveAlert(desc:String,user_id:String,service:String,victims:String)
    fun setMainViewContext(context: Context)
    fun OncreateAlert(desc: String,service:String,victims:String)
    fun sendImage(path:String)
    fun sendVideo(path:String)


}
