package com.example.alertaccident.presentation

import android.content.Context

interface IAddVehiculePresenter {
    fun setMainViewContext(context: Context)
    fun addVehicule(marque:String,immatricule:String,assurance:String,couleur:String,nombrePortes:String)
}