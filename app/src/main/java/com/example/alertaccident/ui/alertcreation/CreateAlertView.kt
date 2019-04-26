package com.example.alertaccident.ui.alertcreation

interface CreateAlertView {
    fun onSuccess(message:String)
    fun onError(message:String)
    fun load()
}