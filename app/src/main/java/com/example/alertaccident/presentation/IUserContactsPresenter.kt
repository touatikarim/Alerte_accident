package com.example.alertaccident.presentation

import android.content.Context
import com.example.alertaccident.model.Contact

interface IUserContactsPresenter {
    fun setUpdateDialog(context: Context,contact:Contact)
    fun setMainViewContext(context: Context)
    fun setDeleteDialog(context:Context,contact: Contact)
}