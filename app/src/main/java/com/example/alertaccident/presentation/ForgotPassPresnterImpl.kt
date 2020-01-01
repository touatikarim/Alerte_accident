package com.example.alertaccident.presentation

import android.content.Context
import android.os.Handler
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.LoginModel
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.ui.Forgotpassword.ForgotPassView
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPassPresnterImpl(internal var ForgotPassview:ForgotPassView):IForgotPassPresenter {
    lateinit var context: Context


    override fun forgetpass(email: String) {
        val password=""
        val user= LoginModel(email,password)
        RetrofitManager.getInstance(Constants.baseurl).service!!.forgetpass(user)
            .enqueue(object :Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {

                       if(response.code()==200){
                           ForgotPassview.navigate()
                           Handler().postDelayed({ ForgotPassview.onSuccess(response.body()!!.message)},1500)
                       }
                       else {
                           val errorJsonString = response.errorBody()?.string()
                           val message = JsonParser().parse(errorJsonString)
                               .asJsonObject["message"]
                               .asString
                           ForgotPassview.load()
                           Handler().postDelayed({ ForgotPassview.onError(message) }, 1500)
                       }

                   }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    ForgotPassview.load()

                    Handler().postDelayed({ForgotPassview.onError(t.message!!)},1500)
                }

            })
    }

    override fun setMainViewContext(context: Context) {
        this.context=context
    }
}