package com.example.alertaccident.presentation

import android.content.Context
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

interface IListeVehiculePresenter {
    fun setMainViewContext(context: Context)
    fun getListVehicule(recyclerView: RecyclerView, emptyRecyclerView: LinearLayout)
}