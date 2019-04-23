package com.example.alertaccident.helper

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.alertaccident.model.User
import com.example.alertaccident.retrofit.UserManager
import com.facebook.AccessToken
import com.facebook.GraphRequest

object UiUtils {

    fun hideKeyboardOntouch(view: View?, activity: Activity) {

           if (view !is EditText && view != null) {
               view.setOnTouchListener(object : View.OnTouchListener {
                   override fun onTouch(v: View, event: MotionEvent): Boolean {
                       try{
                       UiUtils.hideKeyboardByElement(activity)}
                       catch (e: Exception) {
                           Log.d("error", e.message)
                       }
                       return false
                   }
               })
           }
           //If a layout container, iterate over children and seed recursion.
           if (view is ViewGroup) {
               for (i in 0 until (view as ViewGroup).childCount) {
                   val innerView = (view as ViewGroup).getChildAt(i)
                   hideKeyboardOntouch(innerView, activity)
               }
           }

    }

    private fun hideKeyboardByElement(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        try {
            inputMethodManager!!.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )
        } catch (e: Exception) {
            Log.d("heree", e.message)
        }

    }


    fun isDeviceConnectedToInternet(context: Context) : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return (activeNetwork != null)
    }












}