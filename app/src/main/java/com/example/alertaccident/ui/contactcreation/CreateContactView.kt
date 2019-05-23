package com.example.alertaccident.ui.contactcreation

interface CreateContactView {

    fun navigate()
    fun onSuccess(message:String)
    fun onError(message:String)
}