package com.example.alertaccident.presentation






import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.provider.Settings
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.isDataValid
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.login.SigninView
import com.google.gson.JsonParser

import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity


import androidx.fragment.app.Fragment
import com.example.alertaccident.helper.GPSUtils
import com.example.alertaccident.helper.GPSUtils.locationCallback
import com.example.alertaccident.helper.GPSUtils.locationRequest
import com.example.alertaccident.helper.UiUtils.isDeviceConnectedToInternet
import com.example.alertaccident.model.*
import com.example.alertaccident.retrofit.UserManager.saveLoginToken
import com.facebook.*

import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import retrofit2.*
import java.util.*

class LoginPresenterImpl(internal var signinview:SigninView):IloginPresenter
     {


         private var callbackManager: CallbackManager? = null
        lateinit  var context: Context
         lateinit var fusedLocationClient: FusedLocationProviderClient



         override fun login(email:String,password:String) {
             val loginModel = LoginModel(email, password)



                 if (isDataValid(email, password) == -1) {
                     if (isDeviceConnectedToInternet(context)) {
                     RetrofitManager.getInstance(Constants.baseurl).service!!.loginuser(loginModel)
                         .enqueue(object : Callback<ApiResponse> {
                             override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>?) {

                                 if (response != null) {

                                     if (response.code() == 200) {
                                         val id = response.body()!!.data._id
                                         val name = response.body()!!.data.nom
                                         val phone = response.body()!!.data.telephone.toString()
                                         val login_token=response.body()!!.token
                                         saveLoginToken(context,login_token)
                                         val user = User(email, password, name, id, phone)
                                         UserManager.saveCredentials(context, user)
                                         signinview.navigate()
                                         Handler().postDelayed(
                                             { signinview.onSuccess(response.body()!!.message) },
                                             1500
                                         )

                                     } else {
                                         signinview.load()
                                         Handler().postDelayed({ signinview.onError(context.getString(R.string.no_account)) },1500)
//                                         val errorJsonString = response.errorBody()?.string()
//                                         val message = JsonParser().parse(errorJsonString)
//                                             .asJsonObject["message"]
//                                             .asString
//                                         val message=response.body()!!.message
//                                         signinview.load()
//                                         if (message.compareTo("User not found...") == 0)
//                                             Handler().postDelayed(
//                                                 { signinview.onError(context.getString(R.string.no_account)) },
//                                                 1500
//                                             )
//                                         else
//                                             Handler().postDelayed(
//                                                 { signinview.onError(context.getString(R.string.authen_error)) },
//                                                 1500
//                                             )


                                     }
                                 }
                             }

                             override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                                 signinview.onError(t.message!!)//context.getString(R.string.Server))
                             }
                         })

                 }
                     else {
                         signinview.load()
                       Handler().postDelayed({signinview.onError(context.getString(R.string.no_connection))},1500)
                     }
             }

         }


         override fun onLogin(email: String, password: String) {
             val isLoginsucces= isDataValid(email,password)
             if (isLoginsucces==0)
                 signinview.onError(context.getString(R.string.email_address))
             else if(isLoginsucces==2)
                 signinview.onError(context.getString(R.string.nopassword))

         }

         override fun setMainViewContext(context: Context) {
        this.context=context
    }

    override fun signinfb(fragment: Fragment) {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logInWithReadPermissions(fragment, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    if (AccessToken.getCurrentAccessToken() != null){
                        //val activity = context as Activity
                        val request = GraphRequest.newMeRequest(
                            AccessToken.getCurrentAccessToken()
                        ) { jsonObject, _ ->
                            val email = jsonObject?.get("email")?.toString() ?: ""
                            val name = jsonObject.get("name").toString()
                            registerFacebook(name,email,"Mobelite007",loginResult.accessToken.token)
                            UserManager.saveCredentials(context,User(email,"",name,"",""))
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "id,name,email")
                        request.parameters = parameters
                        request.executeAsync()
                    }
                    UserManager.saveFacebookToken(context,loginResult.accessToken.token)

                    signinview.navigate()

                }
                override fun onCancel() {
                    Log.d("msg", "Facebook onCancel.")

                }
                override fun onError(error: FacebookException) {
                    Log.d("msg", "Facebook onError.")

                }
            })

    }


         override fun registerGoogle(nom:String,email:String,password:String,googleToken:String) {
             val registerGoogleModel= RegisterGoogleModel(nom, email, password, googleToken)
             RetrofitManager.getInstance(Constants.baseurl).service!!.registerusergoogle(registerGoogleModel)
                 .enqueue(object :Callback<ApiResponse>{
                     override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                         if (response.isSuccessful){
                             val id = response.body()!!.data._id
                             val user=User(email,password,nom,id,"")
                             UserManager.saveCredentials(context, user)
                             signinview.onSuccess(response.body()!!.message)
                         }
                         else{
                             val errorJsonString = response.errorBody()?.string()
                             val message = JsonParser().parse(errorJsonString)
                                 .asJsonObject["message"]
                                 .asString
                             signinview.load()
                             signinview.onError(message)}
                     }
                     override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                         signinview.onError(t.message!!)
                     }

                 })
         }

         override fun registerFacebook(nom: String, email: String, password: String, FbToken: String) {
            val registerFbModel=RegisterFbModel(nom, email, password,FbToken)
             RetrofitManager.getInstance(Constants.baseurl).service!!.registeruserfacebook(registerFbModel)
                 .enqueue(object : Callback<ApiResponse>{

                     override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                         if(response.isSuccessful){
                             signinview.onSuccess(response.body()!!.message)
                         }
                         else {
                             val errorJsonString = response.errorBody()?.string()
                             val message = JsonParser().parse(errorJsonString)
                                 .asJsonObject["message"]
                                 .asString
                             signinview.load()
                             signinview.onError(message)
                         }

                         }

                     override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                         signinview.onError(t.message!!)
                     }

                 })
         }
         override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
             callbackManager?.onActivityResult(requestCode, resultCode, data)

//
//             val mail=sp.getString("USER_EMAIL","")
//             Log.d("mail",mail)
//             val name=sp.getString("USER_NAME","")
//             v
//             registerFacebook(name,mail,"Mobelite007",token)

         }

         override fun getLocation(activity: Activity) {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                 if (GPSUtils.checkLocationPermission(activity, context)) {
                     GPSUtils.buildLocationRequest()
                     GPSUtils.buildLocationCallback()
                     fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
                     fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                     fusedLocationClient.lastLocation
                         .addOnSuccessListener { location ->
                             val latitude = location?.latitude
                             val longitud = location?.longitude
                             if (latitude != null && longitud != null) {
                                 val geocoder = Geocoder(context)
                                 val adress = geocoder.getFromLocation(latitude, longitud, 10)
                                 val country = adress.get(0).countryName
                                 val city=GPSUtils.getCity(latitude,longitud,context)
                                 val sub = GPSUtils.getarea(latitude,longitud,context)

                                 UserManager.saveplace(country, city, sub, context)
                             }
                             UserManager.saveposition(latitude.toString(), longitud.toString(), context)

                         }
                 }
             }
//              else {
//             GPSUtils.buildLocationRequest()
//               fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
//                 fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
//            }

         }

     }



