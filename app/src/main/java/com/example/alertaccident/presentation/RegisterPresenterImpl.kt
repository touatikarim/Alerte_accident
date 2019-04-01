package com.example.alertaccident.presentation


import android.content.Context
import android.util.Log
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.isRegistrationValid
import com.example.alertaccident.R

import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.RegisterModel
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.ui.register.SignupView
import retrofit2.*



class RegisterPresenterImpl(internal var signupview:SignupView):IregisterPresenter {
    lateinit  var context: Context
   override fun Register(nom: String, password: String, telephone: String, email: String,CIN:String) {
       val register=RegisterModel(nom, password, telephone,
           email,CIN)
       RetrofitManager.getInstance(Constants.baseurl).service!!.registeruser(register).enqueue(object:Callback<ApiResponse> {
           override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>?) {

               if (response!!.isSuccessful) {
                                    signupview.onSuccess(response.body()!!.message)
                                    Log.e("response", response.body().toString())
                                    if (response.code() == 200) {
                                        signupview.navigate()
                                    }
                                }
           }

           override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
               signupview.onError(context.getString(R.string.Server))
           }
       })
   }



    override fun onRegister(email: String, password: String, username: String, phone: String, repeatPassword: String) {
        val isLoginsucces= isRegistrationValid(email,password, username,phone,repeatPassword)
        if (isLoginsucces==0)
            signupview.onError(context.getString(R.string.valid_user))
        else if (isLoginsucces==1)
            signupview.onError(context.getString(R.string.valid_number))
        else if (isLoginsucces==2)
            signupview.onError(context.getString(R.string.valid_address))
        else if(isLoginsucces==3)
            signupview.onError(context.getString(R.string.valid_password))
        else if(isLoginsucces==4)
            signupview.onError(context.getString(R.string.identical_pass))
        else
            signupview.onSuccess(context.getString(R.string.account_creation))
    }

    override fun setMainViewContext(context: Context) {this.context=context }



}
