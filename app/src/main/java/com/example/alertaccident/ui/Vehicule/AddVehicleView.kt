package com.example.alertaccident.ui.Vehicule

interface AddVehicleView {
    fun onSuccess(message:String)
    fun onError(message:String)
    fun load()
    fun navigate()
    fun load_image(state:Int)
}