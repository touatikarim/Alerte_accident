package com.example.alertaccident.database

import android.content.Context
import com.example.alertaccident.model.Contact
import io.reactivex.Flowable

class ContactRepository(private val mLocationDataSource: IContactDataSource):IContactDataSource {
    override fun getContactByname(name: String): Contact {
        return mLocationDataSource.getContactByname(name)
    }

    override fun insertListContact(list: List<Contact>) {
        mLocationDataSource.insertListContact(list)
    }

    override val allContacts: Flowable<List<Contact>>
        get() = mLocationDataSource.allContacts

    override fun getContactById(UserId: String): Flowable<List<Contact>> {
        return mLocationDataSource.getContactById(UserId)
    }

    override fun insertContact(vararg contact: Contact) {
        mLocationDataSource.insertContact(*contact)
    }

    override fun updateContact(vararg contact: Contact) {
        mLocationDataSource.updateContact(*contact)
    }

    override fun deleteContact( contact: Contact) {
        mLocationDataSource.deleteContact(contact)
    }

    override fun deleteAllContact() {
        mLocationDataSource.deleteAllContact()
    }
    companion object {
        private var mInstance:ContactRepository?=null
        fun getInstance(mLocationDataSource: IContactDataSource):ContactRepository{
            if(mInstance==null)
                mInstance= ContactRepository(mLocationDataSource)

            return mInstance!!
        }
    }

}