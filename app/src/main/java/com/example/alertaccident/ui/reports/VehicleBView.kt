package com.example.alertaccident.ui.reports

interface VehicleBView {
    fun onSuccess(message:String)
    fun onError(message:String)
    fun load()
    fun navigate()
}