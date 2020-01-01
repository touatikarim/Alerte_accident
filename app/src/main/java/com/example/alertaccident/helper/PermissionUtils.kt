package com.example.alertaccident.helper

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {
    fun checkPhoneCallPermission(context: Context): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }
    fun requestPhoneCallPermission(context: Context) {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(context as Activity,
            Manifest.permission.CALL_PHONE)

        if (shouldProvideRationale) {
            Log.i("here", "Displaying permission rationale to provide additional context.")

        } else {
            Log.i("here", "Requesting permission")
            ActivityCompat.requestPermissions(context as Activity,
                arrayOf(Manifest.permission.CALL_PHONE),
                Constants.REQUEST_PHONE_CALL)

        }
    }
    fun checkSmsPermission(context: Context): Boolean {
        return (ContextCompat.checkSelfPermission(context, android.Manifest.permission.SEND_SMS) ==
                PackageManager.PERMISSION_GRANTED )
    }
    fun requestSendSmsPermission(context: Context) {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(context as Activity,
            Manifest.permission.SEND_SMS)

        if (shouldProvideRationale) {
            Log.i("here", "Displaying permission rationale to provide additional context.")

        } else {
            Log.i("here", "Requesting permission")
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.SEND_SMS),
                Constants.REQUEST_SEND_SMS)

        }
    }
    fun getCurrentActivity(context: Context):String{
        val am = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val taskInfo=am.getRunningTasks(1)
        return taskInfo.get(0).topActivity.className

    }
}