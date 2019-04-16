package com.example.alertaccident.presentation

import android.content.Context

interface IForgotPassPresenter {
    fun forgetpass(email:String)
    fun setMainViewContext(context: Context)
}