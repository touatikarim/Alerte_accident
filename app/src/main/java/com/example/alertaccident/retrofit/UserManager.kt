package com.example.alertaccident.retrofit

import android.content.Context
import android.content.SharedPreferences
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
        editor.putString("USER_ID", null)
        editor.putString("USER_PWD", null)
        editor.putString("USER_PHONE",null)
        editor.putString("USER_EMAIL", null)
        editor.putString("USER_NAME", null)
        editor.putString("GOOGLE_SIGNED_IN",null)
        editor.putString("FACEBOOK_SIGNED_IN",null)
        editor.putString("SIGN_TOKEN",null)
        editor.putString("USER_LAT",null)
        editor.putString("USER_LNG",null)
        editor.putString("IMAGE_URL",null)
        editor.putString("USER_COUNTRY",null)
        editor.putString("USER_CITY",null)
        editor.putString("USER_AREA",null)
        editor.putString("ALERT_LAT",null)
        editor.putString("ALERT_LNG",null)
        editor.apply()
    }
    fun saveposition(latitude:String,longitude:String,context: Context){
        sharedPref = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val editor= sharedPref.edit()
        editor.putString("USER_LAT",latitude)
        editor.putString("USER_LNG",longitude)
        editor.apply()
    }
    fun saveplace(country:String?,city:String?,area:String?,context: Context){
        sharedPref = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val editor= sharedPref.edit()
        editor.putString("USER_COUNTRY",country)
        editor.putString("USER_CITY",city)
        editor.putString("USER_AREA",area)
        editor.apply()
    }

    fun savealertlocation(latitude: String,longitude: String,context: Context){
        val editor = sharedPref.edit()
        editor.putString("ALERT_LAT",latitude)
        editor.putString("ALERT_LNG",longitude)
        editor.apply()
    }
//    fun clearalertpos(context:Context){
//        sharedPref = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
//        val editor = sharedPref.edit()
//        editor.putString("ALERT_LAT",null)
//        editor.putString("ALERT_LNG",null)
//        editor.apply
//    }





}