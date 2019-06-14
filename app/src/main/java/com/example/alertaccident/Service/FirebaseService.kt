package com.example.alertaccident.Service

import android.graphics.Bitmap
import com.example.alertaccident.R
import com.example.alertaccident.helper.UiUtils
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
        val alertId=p0.data.get("alert_id")
        UserManager.savealertlocation(latitude,longititude,this@FirebaseService)
        UserManager.saveAlert(this@FirebaseService,location,imageurl,date,victims,desc)
        if(!imageurl.isNullOrEmpty()){
        val image=  UiUtils.getBitmapfromUrl(imageurl)
        UiUtils.sendNotification(this@FirebaseService,"Notification",location!!,image,alertId)}
        else {

            UiUtils.sendNotification(this@FirebaseService, "Notification", location!!, null, alertId)
        }


    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        UserManager.saveToken(this@FirebaseService,p0)
    }
}