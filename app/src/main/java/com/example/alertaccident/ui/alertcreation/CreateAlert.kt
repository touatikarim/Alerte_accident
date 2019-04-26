package com.example.alertaccident.ui.alertcreation


import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.alertaccident.R
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.presentation.CreateAlertPresenterImpl
import com.example.alertaccident.presentation.IcreateAlertPresenter
import com.example.alertaccident.retrofit.UserManager
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_create_alert.*
import com.example.alertaccident.helper.Constants


class CreateAlert : Fragment(),CreateAlertView {

    var spinnerdialog: SpinnerDialog?=null
    lateinit var alertpresenter:IcreateAlertPresenter
    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
       Toasty.error(activity!!.baseContext,message,Toast.LENGTH_SHORT).show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view, activity!!)
        alertpresenter=CreateAlertPresenterImpl(this)
        alertpresenter.setMainViewContext(activity!!.baseContext)
        spinnerdialog=SpinnerDialog(activity,Constants.list_of_services,"Select Service",R.style.DialogAnimations_SmileWindow)
        spinnerdialog!!.bindOnSpinerListener{item,position->
            service_chosen.setText(Constants.list_of_services[position])
        }


        val sp = UserManager.getSharedPref(activity!!.baseContext)
        val user_id=sp.getString("USER_ID","")
        val usermail=sp.getString("USER_EMAIL","")
        btn_send.setOnClickListener {
            val description=id_description.text.toString()
            val service=service_chosen.text.toString()
            alertpresenter.saveAlert(description,user_id,service)
        }
        service.setOnClickListener {
            spinnerdialog!!.showSpinerDialog()
        }
    }
    override fun load() {
        val progressBar = send_alert
        progressBar.setVisibility(View.VISIBLE)
        Handler().postDelayed({ progressBar.setVisibility(View.GONE) }, 1500)
    }
}

