package com.example.alertaccident.presentation

import android.content.Context
import com.example.alertaccident.database.ContactRepository

interface ICreateContactPresenter {
    fun addContact(name:String,phone:String)
    fun setMainViewContext(context: Context)
}