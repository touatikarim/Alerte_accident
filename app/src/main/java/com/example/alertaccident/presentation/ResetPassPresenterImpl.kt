package com.example.alertaccident.presentation

import android.content.Context
import android.os.Handler
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.PasswordModel

import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.resetpassword.ResetpassView
import com.google.gson.JsonParser
import retrofit2.*

class ResetPassPresenterImpl(internal var resetpassView: ResetpassView):IResetPassPresenter {
    lateinit var context: Context
    override fun Update_password(password: String,newPassword: String) {
           val passModel=PasswordModel(password,newPassword)
              val sp= UserManager.getSharedPref(context)
              val usermail=sp.getString("USER_EMAIL","")
        RetrofitManager.getInstance(Constants.baseurl).service!!.updatepassword(usermail,passModel)
            .enqueue(object :Callback<ApiResponse>{

                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.code() == 200) {
                            resetpassView.navigate()
                            Handler().postDelayed({ resetpassView.onSuccess(response.body()!!.message) }, 1500)
                        } else {
                            val errorJsonString = response.errorBody()?.string()
                            val message = JsonParser().parse(errorJsonString)
                                .asJsonObject["message"]
                                .asString
                            resetpassView.load()
                            Handler().postDelayed({ resetpassView.onError(message) }, 1500)
                        }



                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        resetpassView.onError(t.toString())
                }

            })
    }

    override fun setMainViewContext(context: Context) {
        this.context=context
    }
}