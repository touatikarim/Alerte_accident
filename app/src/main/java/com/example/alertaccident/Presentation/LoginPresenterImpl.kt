package com.example.alertaccident.presentation


import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.alertaccident.helper.isDataValid
import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.User
import com.example.alertaccident.retrofit.ILogin
import com.example.alertaccident.retrofit.RetrofitClient
import com.example.alertaccident.ui.login.SigninView

import retrofit2.*



class LoginPresenterImpl(internal var signinview:SigninView):IloginPresenter {
    override fun observe():MutableLiveData<String> {
        return signInResponse

    }


    val signInResponse = MutableLiveData<String>()
    override fun login(email:String,password:String) {
        val retrofit = RetrofitClient.getInstance()
        val ilogin = retrofit.create(ILogin::class.java)
        if(isDataValid(email,password)==-1) {
            ilogin.loginuser(User(email, password)).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful)
                    {
                        signinview.onSuccess(response.body()!!.message)
                       signInResponse.postValue(response.message())
                        //Log.e("response",response.message())

                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    signinview.onError(t.message!!)

                }


            })
        }

    }


    override fun onLogin(email: String, password: String) {
    val isLoginsucces= isDataValid(email,password)
    if (isLoginsucces==0)
        signinview.onError("Please insert email address")
    else if (isLoginsucces==1)
        signinview.onError("Please enter a valid email address")
    else if(isLoginsucces==2)
        signinview.onError("Please enter a valid password")


}
}