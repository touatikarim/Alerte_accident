package com.example.alertaccident.database

import com.example.alertaccident.model.Contact
import io.reactivex.Flowable

interface IContactDataSource {
    val allContacts:Flowable<List<Contact>>
    fun getContactById(UserId:String):Flowable<List<Contact>>
    fun insertContact(vararg contact:Contact)
    fun updateContact(vararg contact:Contact)
    fun deleteContact(contact:Contact)
    fun deleteAllContact()

}