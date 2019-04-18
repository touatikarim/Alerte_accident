package com.example.alertaccident.ui.update

interface UpdateprofileView {
    fun onSuccess(message:String)
    fun onError(message:String)
    fun load()
    fun navigate()
}