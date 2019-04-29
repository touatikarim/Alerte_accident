package com.example.alertaccident.presentation

import android.app.Activity
import android.content.Context

interface IHomePresenter {
    fun getLocation(activity: Activity)
    fun setMainViewContext(context: Context)
}