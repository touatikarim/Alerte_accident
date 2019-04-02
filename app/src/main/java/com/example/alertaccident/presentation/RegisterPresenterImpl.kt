package com.example.alertaccident.presentation


import android.content.Context
import android.os.Handler
import android.util.Log
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.isRegistrationValid
import com.example.alertaccident.R

import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.RegisterModel
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.retrofit.RetrofitServices
import com.example.alertaccident.ui.register.SignupView

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

                       if (response!!.isSuccessful) {
                           Handler().postDelayed({signupview.onSuccess(response.body()!!.message)},1500)
                          //Log.e("response", response.body().toString())
                           if (response.code() == 200) {
                               signupview.navigate()
                           }
                       }
                   }

                   override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                       signupview.onError(t.message!!)//context.getString(R.string.Server))
                   }
               })
//           CoroutineScope(Dispatchers.IO).launch {
//               val request =  RetrofitManager.getInstance(Constants.baseurl).service!!.registeruser(register)
//               withContext(Dispatchers.Default) {
//                   try {
//                       val response=request
//                       Log.d("response", response.toString())
//                   } catch (e: HttpException) {
//                       signupview.onError("dsqmkqm")
//
//                   }
//               }
//
//           }
      // }
       }




    override fun onRegister(email: String, password: String, repeatPassword: String,username:String,phone:String) {
        val isRegistersucces= isRegistrationValid(email,password,repeatPassword,username,phone)
       if (isRegistersucces==0)
           signupview.onError(context.getString(R.string.valid_user))
       else if (isRegistersucces==1)
           signupview.onError(context.getString(R.string.valid_number))
       else if (isRegistersucces==2)
           signupview.onError(context.getString(R.string.valid_address))
        else if (isRegistersucces==3)
           signupview.onError(context.getString(R.string.valid_password))
        else if  (isRegistersucces==4)
           signupview.onError(context.getString(R.string.identical_pass))

    }

    override fun setMainViewContext(context: Context) {this.context=context }



}
