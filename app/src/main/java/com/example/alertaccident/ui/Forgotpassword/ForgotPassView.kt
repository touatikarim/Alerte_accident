package com.example.alertaccident.ui.Forgotpassword

interface ForgotPassView {
    fun onSuccess(message:String)
    fun onError(message:String)
    fun navigate()
    fun load()
}