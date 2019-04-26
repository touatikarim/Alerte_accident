package com.example.alertaccident.presentation

import android.content.Context

interface IcreateAlertPresenter {
     fun saveAlert(desc:String,user_id:String,service:String)
    fun setMainViewContext(context: Context)

}
