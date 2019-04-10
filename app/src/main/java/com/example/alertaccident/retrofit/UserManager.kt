package com.example.alertaccident.retrofit

import android.content.Context
import android.content.SharedPreferences
import com.example.alertaccident.model.User

object UserManager {
    private lateinit var sharedPref: SharedPreferences
//    fun saveCredentials(context: Context, user: User)
//    {
//        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//        val editor = sharedPref.edit()
//        editor.putString(USER_EMAIL, user.email)
//        editor.putString(USER_ID, user.id)
//        editor.putString(USER_NAME, user.name)
//        editor.putString(USER_PWD, user.pwd)
//        editor.apply()
//    }
//    fun getSharedPref(context: Context): SharedPreferences
//    {
//        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//        return sharedPref
//    }
}