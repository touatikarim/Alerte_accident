package com.example.alertaccident.presentation






import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.isDataValid

import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.login.SigninView
import com.google.gson.JsonParser
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.alertaccident.helper.GPSUtils
import com.example.alertaccident.helper.GPSUtils.locationCallback
import com.example.alertaccident.helper.GPSUtils.locationRequest
import com.example.alertaccident.helper.UiUtils.isDeviceConnectedToInternet
import com.example.alertaccident.model.*
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.retrofit.UserManager.saveLoginToken
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.location.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import retrofit2.*
import java.util.*

class LoginPresenterImpl(internal var signinview:SigninView):IloginPresenter
     {

         private var callbackManager: CallbackManager? = null
         lateinit  var context: Context
         lateinit var fusedLocationClient: FusedLocationProviderClient
         private fun generateToken(user_id:String){
             var ref:DatabaseReference
             FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
                 if(!it.isSuccessful){
                     Log.d("error"," getInstanceId failed",it.exception)
                 }
                 val token=it.result?.token
                 ref=FirebaseDatabase.getInstance().reference
                 ref.child("Tokens").child(user_id).setValue(token)
             }

            }


         override fun login(email:String,password:String,button:Button) {
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
                                         generateToken(id)
                                         Handler().postDelayed(
                                             { signinview.onSuccess(response.body()!!.message) },
                                             1500
                                         )

                                     } else {

                                         signinview.load()
                                         Handler().postDelayed({ signinview.onError(context.getString(R.string.no_account)) },1500)
                                         button.setEnabled(true)

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
                        val request = GraphRequest.newMeRequest(
                            AccessToken.getCurrentAccessToken()
                        ) { jsonObject, _ ->
                            val email = jsonObject?.get("email")?.toString() ?: ""
                            val name = jsonObject.get("name").toString()
                            registerFacebook(Constants.socialType2,email,loginResult.accessToken.token)
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


         override fun registerGoogle(socialType:String,email:String,googleToken:String) {
             val registerGoogleModel= RegisterGoogleModel(socialType, email, googleToken)
             RetrofitManager.getInstance(Constants.baseurl).service!!.registerusergoogle(registerGoogleModel)
                 .enqueue(object :Callback<ApiResponse>{
                     override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                         if (response.isSuccessful){
                             val sp = UserManager.getSharedPref(context)
                             val name=sp.getString("USER_NAME","")
                             val id = response.body()!!.data._id
                             val user=User(email,"",name,id,"")
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

         override fun registerFacebook(socialType: String, email: String, facebookToken: String) {
            val registerFbModel=RegisterFbModel(socialType,email,facebookToken)
             RetrofitManager.getInstance(Constants.baseurl).service!!.registeruserfacebook(registerFbModel)
                 .enqueue(object : Callback<ApiResponse>{

                     override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                         if(response.isSuccessful){
                             val id =response.body()!!.data._id
                             val sp = UserManager.getSharedPref(context)
                             val name=sp.getString("USER_NAME","")
                             UserManager.saveCredentials(context,User(email,"",name,id,""))
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


         }


     }



