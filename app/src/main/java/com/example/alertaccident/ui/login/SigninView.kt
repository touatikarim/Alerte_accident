package com.example.alertaccident.ui.login

interface SigninView {
    fun onSuccess(message:String)
    fun onError(message:String)
    fun navigate()
    fun load()
}