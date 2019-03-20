package com.example.alertaccident.Presentation

import com.example.alertaccident.Helper.isDataValid
import com.example.alertaccident.Ui.login.SigninView

class LoginPresenterImpl(internal var signinview:SigninView):IloginPresenter {
    override fun onLogin(email: String, password: String) {
    val isLoginsucces= isDataValid(email,password)
    if (isLoginsucces==-1)
        signinview.onLoginResult("Login Success")
     else
        signinview.onLoginResult("Login Error")
    }

}