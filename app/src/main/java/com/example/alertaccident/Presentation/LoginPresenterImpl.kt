package com.example.alertaccident.Presentation

import android.content.Context
import com.example.alertaccident.Helper.isDataValid
import com.example.alertaccident.R
import com.example.alertaccident.Ui.login.SigninView
import kotlin.math.sign

class LoginPresenterImpl(internal var signinview:SigninView):IloginPresenter {

    override fun onLogin(email: String, password: String) {
    val isLoginsucces= isDataValid(email,password)
    if (isLoginsucces==0)
        signinview.onError("Please insert email address")
    else if (isLoginsucces==1)
        signinview.onError("Please enter a valid email address")
    else if (isLoginsucces==2)
        signinview.onError("Please enter a valid password")
    else
        signinview.onSuccess("Login Success")

}
}