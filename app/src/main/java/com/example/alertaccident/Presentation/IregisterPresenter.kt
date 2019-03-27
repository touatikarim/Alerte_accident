package com.example.alertaccident.Presentation

interface IregisterPresenter {
    fun onRegister(email:String,password: String,username: String,phone: String,repeatPassword: String)
}