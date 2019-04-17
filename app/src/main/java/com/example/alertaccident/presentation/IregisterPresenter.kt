package com.example.alertaccident.presentation

import android.content.Context

interface IregisterPresenter {
    fun onRegister(email: String, password: String, repeatPassword: String, username: String, phone: String,CIN: String)
    fun Register(nom: String, password: String, telephone: String, email: String,CIN: String)
    fun setMainViewContext(context: Context)

}