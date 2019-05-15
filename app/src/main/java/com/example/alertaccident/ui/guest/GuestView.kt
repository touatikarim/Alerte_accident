package com.example.alertaccident.ui.guest

interface GuestView {
    fun onSuccess(message:String)
    fun onError(message:String)
    fun load_image(state:Int)
    fun load_alert(state:Int)
    fun navigate()
}