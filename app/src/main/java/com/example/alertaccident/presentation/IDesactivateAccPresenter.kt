package com.example.alertaccident.presentation

import android.content.Context

interface IDesactivateAccPresenter {
    fun onDesactivateAcc(user_id:String)
    fun setMainViewContext(context: Context)
}