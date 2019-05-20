package com.example.alertaccident.Local

import com.example.alertaccident.database.IContactDataSource
import com.example.alertaccident.model.Contact
import com.example.alertaccident.model.User
import io.reactivex.Flowable

class ContactDataSource(private val contactDAO: ContactDAO):IContactDataSource {
    override val allContacts: Flowable<List<Contact>>
        get() = contactDAO.allcontacts

    override fun getContactById(UserId: String): Flowable<List<Contact>> {
        return contactDAO.getContactById(UserId)
    }

    override fun insertContact(vararg contact: Contact) {
        contactDAO.insertContact(*contact)
    }

    override fun updateContact(vararg contact: Contact) {
       contactDAO.updateContact(*contact)
    }

    override fun deleteContact(  contact: Contact) {
        contactDAO.deleteContact(contact)
    }

    override fun deleteAllContact() {
       contactDAO.deleteAllContact()
    }
    companion object {
        private var mInstance:ContactDataSource?=null
        fun getInstance(contactDao:ContactDAO):ContactDataSource{
            if(mInstance==null)
                mInstance= ContactDataSource(contactDao)

            return mInstance!!
        }
    }
}