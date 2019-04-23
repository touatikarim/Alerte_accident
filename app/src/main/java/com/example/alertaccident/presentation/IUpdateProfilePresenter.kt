package com.example.alertaccident.presentation

import android.content.Context

interface IUpdateProfilePresenter{
    fun Updateprofile(nom:String,email:String,telephone:String)
    fun setMainViewContext(context: Context)
    fun OnUpdate(nom:String,telephone: String)

}