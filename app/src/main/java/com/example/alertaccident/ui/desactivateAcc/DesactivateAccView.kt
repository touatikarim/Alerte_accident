package com.example.alertaccident.ui.desactivateAcc

interface DesactivateAccView {
    fun onSuccess(message:String)
    fun onError(message:String)
    fun navigate()
    fun load()
}