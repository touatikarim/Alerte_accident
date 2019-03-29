package com.example.alertaccident.presentation



import android.os.Handler
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.isDataValid
import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.User
import com.example.alertaccident.retrofit.RetrofitServices
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.ui.login.SigninView

import retrofit2.*



class LoginPresenterImpl(internal var signinview:SigninView):IloginPresenter {

    override fun login(email:String,password:String) {

        var user = User(email, password)
        RetrofitManager.getInstance(Constants.baseurl).service!!.loginuser(user).enqueue(object:Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>?) {

                if (response!!.isSuccessful)
                    {
                        Handler().postDelayed({ signinview.onSuccess(response.body()!!.message)},1500)
                        if (response.code()==200)
                        {signinview.navigate()}

                    }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                signinview.onError("Server Error ")
            }
        })




//        val retrofit = RetrofitManager.getInstance(url)
//        val ilogin = retrofit.create(RetrofitServices::class.java)
//        //if(isDataValid(email,password)==-1) {
//            ilogin.loginuser(User(email, password)).enqueue(object:Callback<ApiResponse>{
//                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                    signinview.onError("Server Error ")
//                }
//
//                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
//                    if (response.isSuccessful)
//                    {
//                        Handler().postDelayed({ signinview.onSuccess(response.body()!!.message)},1500)
//                        if (response.code()==200)
//                        {signinview.navigate()}
//
//                    }
//                }
//            })
//       // }

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