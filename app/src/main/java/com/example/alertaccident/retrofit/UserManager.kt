package com.example.alertaccident.retrofit

import android.content.Context
import android.content.SharedPreferences
import com.example.alertaccident.model.RegisterModel
import com.example.alertaccident.model.User

object UserManager {
    private lateinit var sharedPref: SharedPreferences
    fun saveCredentials(context: Context, user: User)
    {
        sharedPref = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("USER_EMAIL", user.email)
        editor.putString("USER_ID", user.id)
        editor.putString("USER_NAME", user.name)
        editor.putString("USER_PWD", user.pwd)
        editor.putString("USER_PHONE",user.telephone)
        editor.apply()

    }
    fun getSharedPref(context: Context): SharedPreferences
    {
        sharedPref = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        return sharedPref
    }
    fun saveFacebookToken(context: Context, token: String)
    {
        sharedPref = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val editor= sharedPref.edit()
        editor.putString("FACEBOOK_SIGNED_IN", token)
        editor.putString("GOOGLE_SIGNED_IN",null)
        editor.putString("SIGN_TOKEN",null)
        editor.apply()
    }
    fun saveGoogleToken(context: Context, token: String,imgurl:String)
    {
        sharedPref = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val editor= sharedPref.edit()
        editor.putString("FACEBOOK_SIGNED_IN", null)
        editor.putString("GOOGLE_SIGNED_IN",token)
        editor.putString("IMAGE_URL",imgurl)
        editor.putString("SIGN_TOKEN",null)

        editor.apply()
    }

    fun saveLoginToken(context: Context,token:String){
        sharedPref=context.getSharedPreferences("PREF_NAME",Context.MODE_PRIVATE)
        val editor= sharedPref.edit()
        editor.putString("SIGN_TOKEN",token)
        editor.putString("GOOGLE_SIGNED_IN",null)
        editor.putString("FACEBOOK_SIGNED_IN", null)
        editor.apply()

    }
    fun clearSharedPref(context:Context)
    {
        sharedPref = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("USER_EMAIL", null)
        editor.putString("USER_NAME", null)
        editor.putString("GOOGLE_SIGNED_IN",null)
        editor.putString("FACEBOOK_SIGNED_IN",null)
        editor.putString("SIGN_TOKEN",null)
        editor.putString("IMAGE_URL",null)
        editor.apply()
    }


}