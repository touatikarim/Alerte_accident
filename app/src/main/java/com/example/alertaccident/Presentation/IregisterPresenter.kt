package com.example.alertaccident.presentation

interface IregisterPresenter {
    fun onRegister(email:String,password: String,username: String,phone: String,repeatPassword: String)
}