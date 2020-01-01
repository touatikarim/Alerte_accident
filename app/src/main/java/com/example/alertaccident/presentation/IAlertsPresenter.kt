package com.example.alertaccident.presentation

import android.content.Context
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

interface IAlertsPresenter {
    fun showAlerts(recyclerView: RecyclerView,emptyRecyclerView: LinearLayout)
    fun setMainViewContext(context: Context)
}