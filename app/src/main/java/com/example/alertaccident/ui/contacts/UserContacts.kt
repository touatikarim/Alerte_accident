package com.example.alertaccident.ui.contacts


import android.app.ActionBar
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.alertaccident.Local.ContactDataSource
import com.example.alertaccident.Local.ContactDatabase

import com.example.alertaccident.R
import com.example.alertaccident.database.ContactRepository
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.model.Contact
import com.example.alertaccident.presentation.IUserContactsPresenter
import com.example.alertaccident.presentation.UserContactsPresenterImpl
import com.example.alertaccident.retrofit.UserManager
import com.google.android.gms.common.data.DataBufferObserver
import es.dmoral.toasty.Toasty
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user_contacts.*
import java.util.*


class UserContacts : Fragment(),UserContactView {
    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    lateinit var adapter: ArrayAdapter<*>
    var contactList: MutableList<Contact> = ArrayList()
    lateinit var user_id:String
    private var compositeDisposable: CompositeDisposable? = null
    private var contactRepository: ContactRepository? = null
    lateinit var userContactsPresenterImpl: IUserContactsPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userContactsPresenterImpl=UserContactsPresenterImpl(this)
        userContactsPresenterImpl.setMainViewContext(activity!!)
        compositeDisposable = CompositeDisposable()
        adapter = ArrayAdapter(activity!!.baseContext, android.R.layout.simple_list_item_1, contactList)
        registerForContextMenu(lst_contact)
        lst_contact.adapter = adapter
        //Database
        val contactDatabase = ContactDatabase.getInstance(activity!!.baseContext)
        contactRepository = ContactRepository.getInstance(ContactDataSource.getInstance(contactDatabase.contactDAO()))
        val sp = UserManager.getSharedPref(activity!!.baseContext)
         user_id = sp.getString("USER_ID", "")
        loadData()
        fab_add.setOnClickListener {
            findNavController().navigate(R.id.action_contact_dest_to_create_Contact,null,Constants.options)

        }

    }

    private fun loadData() {
        val disposable = contactRepository!!.getContactById(user_id)
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

    override fun onDestroyView() {
    compositeDisposable!!.clear()
        super.onDestroyView()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val info= menuInfo as AdapterView.AdapterContextMenuInfo
        menu.setHeaderTitle("Select action:")
        menu.add(Menu.NONE,0,Menu.NONE,"Update")
        menu.add(Menu.NONE,1,Menu.NONE,"Delete")
        menu.add(Menu.NONE,2,Menu.NONE,"Call")

    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info=item.menuInfo as AdapterView.AdapterContextMenuInfo
        val contact= contactList[info.position]
        when(item.itemId){
            0 ->
            { userContactsPresenterImpl.setUpdateDialog(activity!!,contact) }
            1->
            {userContactsPresenterImpl.setDeleteDialog(activity!!,contact)}
            2->
            {userContactsPresenterImpl.callContact(contact)}
        }
        return true
    }


}
