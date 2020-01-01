package com.example.alertaccident.retrofit

import android.content.Context
import android.content.SharedPreferences
import com.example.alertaccident.model.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


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

    fun savealertlocation(latitude: String?,longitude: String?,context: Context){
        val editor = sharedPref.edit()
        editor.putString("ALERT_LAT",latitude)
        editor.putString("ALERT_LNG",longitude)
        editor.apply()
    }
    fun clearalertpos(context:Context){
        sharedPref = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("ALERT_LAT",null)
        editor.putString("ALERT_LNG",null)
        editor.apply()
    }

    fun saveToken(context:Context,token: String?) {
        val sp = UserManager.getSharedPref(context)
        val id=sp.getString("USER_ID","")
        val database: DatabaseReference = FirebaseDatabase.getInstance().reference
        try {
            database.child("Tokens").child(id).removeValue()
            database.child("Tokens").child(id).setValue(token)
        }
        catch(e: Exception) {

        }
    }
    fun saveAlert(context: Context,location:String?,imageurl:String?, date:String?, victims:String?,desc:String?){
        sharedPref = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val editor= sharedPref.edit()
        editor.putString("NOTIF_LOCATION",location)
        editor.putString("NOTIF_IMAGE",imageurl)
        editor.putString("NOTIF_DATE",date)
        editor.putString("NOTIF_Victims",victims)
        editor.putString("NOTIF_DESC",desc)

        editor.apply()
    }

    fun savecurrentAlertId(context:Context,AlertId:String?){
        sharedPref = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val editor= sharedPref.edit()
        editor.putString("LAST_ALERT_ID",AlertId)
        editor.apply()
    }

    fun getAccidentDetectionService(context: Context): Boolean {
        val o = getSharedPref(context).getBoolean("AUTO_DETECT_ACCIDENTS",false)
        return o
    }
    fun setAccidentDetectionService(b: Boolean, context: Context) {
        getSharedPref(context).edit().putBoolean("AUTO_DETECT_ACCIDENTS", b).apply()
    }
    fun getNotifService(context: Context): Boolean {
        val o = getSharedPref(context).getBoolean("NOTIF_SERVICE",false)
        return o
    }
    fun setNotifService(b: Boolean, context: Context) {
        getSharedPref(context).edit().putBoolean("NOTIF_SERVICE", b).apply()
    }
    fun getAlertPic(context: Context):String{
        val ch= getSharedPref(context).getString("PIC_URL","")
        return  ch
    }
    fun setAlertPic(ch:String?,context: Context){
        getSharedPref(context).edit().putString("PIC_URL",ch).apply()
    }
    fun setDetectCrash(b:Boolean,context: Context){
        getSharedPref(context).edit().putBoolean("CRASH_ACCURED",b).apply()
    }
    fun getDetectCrash(context: Context):Boolean{
      val  b=getSharedPref(context).getBoolean("CRASH_ACCURED",false)
        return b
    }
    fun saveVehicle(context:Context,color:String,assurance:String,name:String,number:String){
        sharedPref=context.getSharedPreferences("PREF_NAME",Context.MODE_PRIVATE)
        val editor= sharedPref.edit()
        editor.putString("CAR_COLOR",color)
        editor.putString("CAR_INSURANCE",assurance)
        editor.putString("CAR_NAME",name)
        editor.putString("CAR_NUMBER",number)
        editor.apply()
    }



}