package com.example.alertaccident.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.alertaccident.Local.ContactDataSource
import com.example.alertaccident.Local.ContactDatabase
import com.example.alertaccident.R
import com.example.alertaccident.database.ContactRepository
import com.example.alertaccident.helper.PermissionUtils
import com.example.alertaccident.model.Contact
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.contacts.UserContactView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class UserContactsPresenterImpl(internal var userContactView: UserContactView):IUserContactsPresenter {


    lateinit var context:Context
    private var compositeDisposable: CompositeDisposable? = null
    lateinit var contactRepository: ContactRepository


    override fun setDeleteDialog(context: Context, contact: Contact) {
        val layout= LinearLayout(context)
        layout.orientation= LinearLayout.VERTICAL
        val params= LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(30,0,30,30)
        val edtPhone= TextView(context)


        edtPhone.text=context.getString(R.string.deletemsg)

        layout.addView(edtPhone,params)
        AlertDialog.Builder(context)
            .setTitle("Delete Contact:")
            .setView(layout)
            .setPositiveButton("Yes"){DialogInterface,which ->
                    DeleteContact(contact)

            }
            .setNegativeButton("No"){
                    dialog,which -> dialog.dismiss()
            }.create()
            .show()
    }

    private fun DeleteContact(contact: Contact) {
        val contactDatabase = ContactDatabase.getInstance(context)
        contactRepository = ContactRepository.getInstance(ContactDataSource.getInstance(contactDatabase.contactDAO()))
        compositeDisposable= CompositeDisposable()
        val disposable = Observable.create(ObservableOnSubscribe<Any>{ e->
            contactRepository!!.deleteContact(contact)
            e.onComplete()
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({}, { throwable->Log.d("error",throwable.message) }, { userContactView.onSuccess("Contact Deleted") })
        compositeDisposable!!.addAll(disposable)
    }


    override fun setUpdateDialog(context: Context,contact:Contact) {
        val layout= LinearLayout(context)
        layout.orientation= LinearLayout.VERTICAL
        val params= LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(30,0,30,30)
        val edtName= EditText(context)
        edtName.hint=contact.name
        layout.addView(edtName,params)
        val edtPhone= EditText(context)
        edtPhone.setInputType(InputType.TYPE_CLASS_PHONE)
        edtPhone.hint=contact.Phone_Number
        layout.addView(edtPhone,params)
        AlertDialog.Builder(context)
            .setTitle("Edit your contact:")
            .setView(layout)
            .setPositiveButton("ok"){DialogInterface,which ->
                if(TextUtils.isEmpty((edtName.text.toString())) || TextUtils.isEmpty((edtPhone.text.toString())) )
                    Log.d("error","msggg")
                else {
                    contact.name=edtName.text.toString()
                    contact.Phone_Number=edtPhone.text.toString()
                    UpdateContact(contact)
                }
            }
            .setNegativeButton("Cancel"){
                    dialog,which -> dialog.dismiss()
            }.create()
            .show()
    }

    private fun UpdateContact(contact: Contact) {
        val contactDatabase = ContactDatabase.getInstance(context)
        contactRepository = ContactRepository.getInstance(ContactDataSource.getInstance(contactDatabase.contactDAO()))
        compositeDisposable= CompositeDisposable()
        val disposable = Observable.create(ObservableOnSubscribe<Any>{ e->
            contactRepository!!.updateContact(contact)
            e.onComplete()
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({}, { throwable->Log.d("error",throwable.message) }, { userContactView.onSuccess("Contact Updated") })
        compositeDisposable!!.addAll(disposable)
    }
    override fun callContact(contact: Contact) {
        if(!PermissionUtils.checkPhoneCallPermission(context))
            PermissionUtils.requestPhoneCallPermission(context)
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${contact.Phone_Number}")
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            (context as Activity).startActivity(intent)
        }
    }




    override fun setMainViewContext(context: Context) {
        this.context=context
    }

}