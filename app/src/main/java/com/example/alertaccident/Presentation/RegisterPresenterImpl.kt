package com.example.alertaccident.Presentation

import com.example.alertaccident.Helper.isRegistrationValid
import com.example.alertaccident.Ui.Register.SignupView

class RegisterPresenterImpl(internal var signupview:SignupView):IregisterPresenter {
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