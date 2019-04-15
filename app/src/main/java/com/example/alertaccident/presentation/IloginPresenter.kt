package com.example.alertaccident.presentation

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment


interface IloginPresenter {
    fun onLogin(email:String,password:String)
    fun login(email:String,password:String)
    fun setMainViewContext(context: Context)
    fun signinfb(fragment:Fragment)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)





}