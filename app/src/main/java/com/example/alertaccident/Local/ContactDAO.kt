package com.example.alertaccident.Local

import androidx.room.*
import com.example.alertaccident.model.Contact
import io.reactivex.Flowable


@Dao
interface ContactDAO {

    @get:Query("SELECT * FROM contacts")
        val allcontacts:Flowable<List<Contact>>

    @Query("SELECT * FROM contacts WHERE email=:UserId")
    fun getContactById(UserId:String):Flowable<List<Contact>>


    @Insert
    fun insertContact(vararg contact: Contact)

    @Update
    fun updateContact(vararg contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("DELETE  FROM contacts ")
    fun deleteAllContact()
}