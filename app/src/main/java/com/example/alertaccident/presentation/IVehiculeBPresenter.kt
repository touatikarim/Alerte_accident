package com.example.alertaccident.presentation

import android.content.Context

interface IVehiculeBPresenter {
    fun setMainViewContext(context: Context)
    fun sendReport(nameA:String,lastnameA:String,addressA:String,marqueA:String,assuranceA:String,couleurA:String,nameB:String,lastnameB:String,addressB:String,marqueB:String,assuranceB:String,couleurB:String,numeroPermisB:String,date:String,localisation:String)
}