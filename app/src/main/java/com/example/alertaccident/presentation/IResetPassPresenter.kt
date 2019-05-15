package com.example.alertaccident.presentation

import android.content.Context

interface IResetPassPresenter {
    fun Update_password(password:String,newPassword:String)
    fun setMainViewContext(context: Context)
}