package com.example.alertaccident.presentation



import android.content.Context
import android.os.Handler
import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.isDataValid
import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.User
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.ui.login.SigninView

import retrofit2.*



class LoginPresenterImpl(internal var signinview:SigninView):IloginPresenter {


    lateinit  var context: Context
    override fun login(email:String,password:String) {

        var user = User(email, password)
        if (isDataValid(email, password) == -1) {
            RetrofitManager.getInstance(Constants.baseurl).service!!.loginuser(user)
                .enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>?) {

                        if (response!!.isSuccessful) {
                            Handler().postDelayed({ signinview.onSuccess(response.body()!!.message) }, 1500)
                            if (response.code() == 200) {
                                signinview.navigate()
                            }

                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        signinview.onError(context.getString(R.string.Server))
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
            signinview.onError(context.getString(R.string.valid_password))


}
    override fun setMainViewContext(context: Context) {
        this.context=context
    }
}