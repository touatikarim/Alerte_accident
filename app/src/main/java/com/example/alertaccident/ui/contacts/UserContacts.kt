package com.example.alertaccident.ui.contacts


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.alertaccident.Local.ContactDataSource
import com.example.alertaccident.Local.ContactDatabase

import com.example.alertaccident.R
import com.example.alertaccident.database.ContactRepository
import com.example.alertaccident.model.Contact
import com.google.android.gms.common.data.DataBufferObserver
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user_contacts.*
import java.util.*


class UserContacts : Fragment() {

    lateinit var adapter: ArrayAdapter<*>
    var contactList: MutableList<Contact> = ArrayList()

    private var compositeDisposable: CompositeDisposable? = null
    private var contactRepository: ContactRepository? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable = CompositeDisposable()

        adapter = ArrayAdapter(activity!!.baseContext, android.R.layout.simple_list_item_1, contactList)
        registerForContextMenu(lst_contact)
        lst_contact.adapter = adapter

        //Database
        val contactDatabase = ContactDatabase.getInstance(activity!!.baseContext)
        contactRepository = ContactRepository.getInstance(ContactDataSource.getInstance(contactDatabase.contactDAO()))

        loadData()
        fab_add.setOnClickListener {
            val disposable = Observable.create(ObservableOnSubscribe<Any> { e ->
                val contact = Contact()
                contact.name="ryma"
                contact.Phone_Number = "95750850"
                contact.email = "ryma@gmail.com"
                contact.id="lll"
                contactRepository!!.insertContact(contact)
                e.onComplete()
            })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe( io.reactivex.functions.Consumer {
                    //success
                },
                    io.reactivex.functions.Consumer {
                        throwable->Log.d("error",throwable.message)
                    },
                    Action { loadData() })

            compositeDisposable!!.addAll(disposable)


        }
    }

    private fun loadData() {
        val disposable = contactRepository!!.allContacts
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
        adapter.notifyDataSetChanged()
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.clear ->deleteAllUsers()
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun deleteAllUsers() {
//
//    }
    override fun onDestroyView() {
    compositeDisposable!!.clear()
        super.onDestroyView()
    }
}
