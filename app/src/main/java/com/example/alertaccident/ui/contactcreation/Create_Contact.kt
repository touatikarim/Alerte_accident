package com.example.alertaccident.ui.contactcreation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.alertaccident.Local.ContactDataSource
import com.example.alertaccident.Local.ContactDatabase

import com.example.alertaccident.R
import com.example.alertaccident.database.ContactRepository
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.presentation.CreateContactPresenterImpl
import com.example.alertaccident.presentation.ICreateContactPresenter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_create__contact.*


class Create_Contact : Fragment(),CreateContactView {
    override fun navigate() {
        findNavController().navigate(R.id.action_create_Contact_to_contact_dest,null,Constants.options)
    }

    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    lateinit var createcontactpresenter:ICreateContactPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create__contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view, activity!!)
        createcontactpresenter=CreateContactPresenterImpl(this)
        createcontactpresenter.setMainViewContext(activity!!.baseContext)
        btn_add.setOnClickListener {
            val name=id_name.text.toString()
            val phone=number.text.toString()
            createcontactpresenter.addContact(name,phone)

        }
    }
}
