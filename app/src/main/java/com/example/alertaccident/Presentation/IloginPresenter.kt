package com.example.alertaccident.presentation

import androidx.lifecycle.MutableLiveData

interface IloginPresenter {
    fun onLogin(email:String,password:String)
    fun login(email:String,password:String)


}