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
        UiUtils.sendNotification(this@FirebaseService,"Notification",location!!)



    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        UserManager.saveToken(this@FirebaseService,p0)
    }
}