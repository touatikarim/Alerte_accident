package com.example.alertaccident.presentation


import android.util.Log
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.isRegistrationValid

import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.RegisterModel
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.ui.register.SignupView
import retrofit2.*



class RegisterPresenterImpl(internal var signupview:SignupView):IregisterPresenter {

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
               signupview.onError(t.message!!)
           }
       })
   }







    override fun onRegister(email: String, password: String, username: String, phone: String, repeatPassword: String) {
        val isLoginsucces= isRegistrationValid(email,password, username,phone,repeatPassword)
        if (isLoginsucces==0)
            signupview.onError("Please enter a valid username")
        else if (isLoginsucces==1)
            signupview.onError("Please enter a valid phone number")
        else if (isLoginsucces==2)
            signupview.onError("Please enter a valid adress email")
        else if(isLoginsucces==3)
            signupview.onError("Please enter a valid password")
        else if(isLoginsucces==4)
            signupview.onError("passwords are not identical")
        else
            signupview.onSuccess("Account Created")
    }


}
