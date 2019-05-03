package com.example.alertaccident.ui.alertcreation

interface CreateAlertView {
    fun onSuccess(message:String)
    fun onError(message:String)
    fun load_image(state:Int)
    fun load_alert(state:Int)
    fun load_video(state: Int)

}