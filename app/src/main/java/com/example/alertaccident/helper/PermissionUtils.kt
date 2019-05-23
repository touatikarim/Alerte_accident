package com.example.alertaccident.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat

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
}