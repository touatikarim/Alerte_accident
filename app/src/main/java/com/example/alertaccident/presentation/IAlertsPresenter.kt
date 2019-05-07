package com.example.alertaccident.presentation

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

interface IAlertsPresenter {
    fun showAlerts(recyclerView: RecyclerView)
    fun setMainViewContext(context: Context)
}