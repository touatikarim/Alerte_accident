package com.example.alertaccident.presentation


import android.content.Context
import android.os.Handler
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.isRegistrationValid
import com.example.alertaccident.R

import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.RegisterGoogleModel
import com.example.alertaccident.model.RegisterModel
import com.example.alertaccident.retrofit.RetrofitManager

import com.example.alertaccident.ui.register.SignupView
import com.google.gson.JsonParser

import retrofit2.*



class RegisterPresenterImpl(internal var signupview:SignupView):IregisterPresenter {


    lateinit  var context: Context
   override fun Register(nom: String, password: String, telephone: String, email: String,CIN:String) {
       val register=RegisterModel(nom, password, telephone,
           email,CIN)
      // if(isRegistrationValid(email,password,repeatPassword,telephone,nom)==-1) {

           RetrofitManager.getInstance(Constants.baseurl).service!!.registeruser(register)
               .enqueue(object : Callback<ApiResponse> {
                   override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>?) {
                       if (response != null) {
                           if (response.code() == 200) {
                               signupview.navigate()
                               Handler().postDelayed({ signupview.onSuccess(response.body()!!.message) }, 1500)
                           } else {
                               val errorJsonString = response.errorBody()?.string()
                               var message = JsonParser().parse(errorJsonString)
                                   .asJsonObject["message"]
                                   .asString
                               signupview.load()
                               if(message.compareTo("Utilisateur existe déjà...")==0)
                                   Handler().postDelayed({ signupview.onError(context.getString(R.string.alreadyregistred)) }, 1500)
                           }
                       }

                   }

                   override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                       signupview.onError(t.message!!)//context.getString(R.string.Server))
                   }
               })

       }




    override fun onRegister(email: String, password: String, repeatPassword: String, username: String, phone: String,CIN: String) {
        val isRegistersucces = isRegistrationValid(email, password, repeatPassword, username, phone,CIN)
        if (isRegistersucces == 0)
            signupview.onError(context.getString(R.string.valid_user))
        if (isRegistersucces == 1)
            signupview.onError(context.getString(R.string.valid_number))
        if(isRegistersucces==2)
            signupview.onError(context.getString(R.string.valid_CIN))
        if (isRegistersucces == 3)
            signupview.onError(context.getString(R.string.email_address))
        if (isRegistersucces == 4)
            signupview.onError(context.getString(R.string.valid_address))
        if (isRegistersucces == 5)
            signupview.onError(context.getString(R.string.nopassword))
        if(isRegistersucces==6)
            signupview.onError(context.getString(R.string.valid_password))
        if(isRegistersucces==7)
            signupview.onError(context.getString(R.string.identical_pass))


    }


    override fun setMainViewContext(context: Context) {this.context=context }



}
