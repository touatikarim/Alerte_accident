package com.example.alertaccident.ui.register

interface SignupView {
  fun onSuccess(message:String)
  fun onError(message:String)
  fun navigate()
  fun load()
}
