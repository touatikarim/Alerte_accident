package com.example.alertaccident.presentation

interface IregisterPresenter {
    fun onRegister(email: String, password: String, username: String, phone: String, repeatPassword: String)
    fun Register( nom:String, password:String, telephone:String, email:String,CIN:String)
}