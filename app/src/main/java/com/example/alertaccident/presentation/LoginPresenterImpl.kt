package com.example.alertaccident.presentation





import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.isDataValid
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.login.SigninView
import com.google.gson.JsonParser

import android.util.Log


import androidx.fragment.app.Fragment
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.model.*
import com.facebook.*

import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import retrofit2.*
import java.util.*

class LoginPresenterImpl(internal var signinview:SigninView):IloginPresenter
     {


         private var callbackManager: CallbackManager? = null
        lateinit  var context: Context


         override fun login(email:String,password:String) {
        val loginModel = LoginModel(email, password)



        if (isDataValid(email, password) == -1) {
            RetrofitManager.getInstance(Constants.baseurl).service!!.loginuser(loginModel)
                .enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>?) {

                        if (response != null) {

                            if (response.code() == 200) {
                                val id=response.body()!!.data._id
                                val name=response.body()!!.data.nom
                                val user = User(email,password,name,id)
                                UserManager.saveCredentials(context,user)
                                signinview.navigate()
                                Handler().postDelayed({ signinview.onSuccess(response.body()!!.message) }, 1500)

                            } else {
                                val errorJsonString = response.errorBody()?.string()
                                val message = JsonParser().parse(errorJsonString)
                                    .asJsonObject["message"]
                                    .asString
                                signinview.load()
                                if(message.compareTo("User not found...")==0)
                                    Handler().postDelayed({ signinview.onError(context.getString(R.string.no_account)) }, 1500)
                                else
                                    Handler().postDelayed({ signinview.onError(context.getString(R.string.authen_error)) }, 1500)




                            }
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        signinview.onError(t.message!!)//context.getString(R.string.Server))
                    }
                })

       }
    }


         override fun onLogin(email: String, password: String) {
             val isLoginsucces= isDataValid(email,password)
             if (isLoginsucces==0)
                 signinview.onError(context.getString(R.string.email_address))
             else if (isLoginsucces==1)
                 signinview.onError(context.getString(R.string.valid_address))
             else if(isLoginsucces==2)
                 signinview.onError(context.getString(R.string.nopassword))
             else if(isLoginsucces==3)
                 signinview.onError(context.getString(R.string.valid_password))


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
                        val activity = context as Activity
                        val request = GraphRequest.newMeRequest(
                            AccessToken.getCurrentAccessToken()
                        ) { jsonObject, _ ->
                            val email = jsonObject?.get("email")?.toString() ?: ""
                            val name = jsonObject.get("name").toString()
                            registerFacebook(name,email,"Mobelite007",loginResult.accessToken.token)
                            UserManager.saveCredentials(context,User(email,"",name,""))
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



}