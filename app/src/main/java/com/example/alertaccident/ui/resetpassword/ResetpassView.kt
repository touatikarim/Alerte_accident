package com.example.alertaccident.ui.resetpassword

interface ResetpassView {
    fun onSuccess(message:String)
    fun onError(message:String)
    fun navigate()
    fun load()
}