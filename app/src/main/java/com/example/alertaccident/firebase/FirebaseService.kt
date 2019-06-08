package com.example.alertaccident.firebase

import android.content.Context
import android.util.Log
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.model.AlertModel
import com.example.alertaccident.retrofit.UserManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService:FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        val location=p0!!.data!!.get("location")
        val imageurl=p0!!.data!!.get("imageurl")
        val date=p0.data.get("date")
        val latitude=p0.data.get("latitude")
        val longititude=p0.data.get("longitude")
        val victims=p0.data.get("victims")
        val desc=p0.data.get("desc")
        UserManager.savealertlocation(latitude,longititude,this@FirebaseService)
        UserManager.saveAlert(this@FirebaseService,location,imageurl,date,victims,desc)
        val image=  UiUtils.getBitmapfromUrl(imageurl!!)
        UiUtils.sendNotification(this@FirebaseService,"Notification",location!!,image!!)



    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        UserManager.saveToken(this@FirebaseService,p0)
    }
}