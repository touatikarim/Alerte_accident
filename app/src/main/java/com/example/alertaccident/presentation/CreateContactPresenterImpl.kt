package com.example.alertaccident.presentation

import android.content.Context
import android.util.Log
import com.example.alertaccident.Local.ContactDataSource
import com.example.alertaccident.Local.ContactDatabase
import com.example.alertaccident.database.ContactRepository
import com.example.alertaccident.model.Contact
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.contactcreation.CreateContactView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class CreateContactPresenterImpl(internal var createContactView: CreateContactView):ICreateContactPresenter {


    private var compositeDisposable: CompositeDisposable? = null
    var contactList: MutableList<Contact> = ArrayList()
    lateinit var contactRepository: ContactRepository
    lateinit var user_id:String
    lateinit var context:Context
    override fun addContact(name:String,phone:String) {
        val contactDatabase = ContactDatabase.getInstance(context)
        contactRepository = ContactRepository.getInstance(ContactDataSource.getInstance(contactDatabase.contactDAO()))
        val sp = UserManager.getSharedPref(context)
        user_id = sp.getString("USER_ID", "")
        compositeDisposable= CompositeDisposable()
        val disposable = Observable.create(ObservableOnSubscribe<Any>{e->
            val contact = Contact()
                contact.name=name
                contact.Phone_Number = phone
                contact.email = user_id
                contact.id=UUID.randomUUID().toString()
                contactRepository!!.insertContact(contact)
                e.onComplete()
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                },
                    {
                        throwable->Log.d("error",throwable.message)
                    },
                    {  loadData()
                        createContactView.onSuccess("Contact saved")
                        createContactView.navigate() })
        compositeDisposable!!.add(disposable)
    }
     fun loadData() {
        val disposable = contactRepository!!.showContactById(user_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ contacts -> onGetAllContactSuccess(contacts) })
            { throwable ->
                Log.d("error", throwable.message)
            }
        compositeDisposable!!.add(disposable)
    }

    private fun onGetAllContactSuccess(contacts: List<Contact>) {
        contactList.clear()
        contactList.addAll(contacts)

    }
    override fun setMainViewContext(context: Context) {
        this.context=context
    }
}
