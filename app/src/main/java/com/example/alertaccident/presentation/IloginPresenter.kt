package com.example.alertaccident.presentation

import android.content.Context


interface IloginPresenter {
    fun onLogin(email:String,password:String)
    fun login(email:String,password:String)
    fun setMainViewContext(context: Context)


}