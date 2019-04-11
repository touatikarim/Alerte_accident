package com.example.alertaccident.ui.update


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.alertaccident.R
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.presentation.IUpdateProfilePresenter
import com.example.alertaccident.presentation.UpdateProfilePresenterImpl
import com.example.alertaccident.retrofit.UserManager
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_update_profile.*


class Update_profile : Fragment(),UpdateprofileView {
    override fun load() {
        val progressBar=update
        progressBar.setVisibility(View.VISIBLE)
        Handler().postDelayed({progressBar.setVisibility(View.GONE)},1500)
    }

    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }


    internal lateinit var updateprofilePresenter : IUpdateProfilePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view,activity!!)
        updateprofilePresenter=UpdateProfilePresenterImpl(this)
        updateprofilePresenter.setMainViewContext(activity!!.baseContext)
        val sp= UserManager.getSharedPref(activity!!.baseContext)
        val usermail=sp.getString("USER_EMAIL","")
        btn_sub.setOnClickListener {
           val name = name_id.text.toString()
           val email = email_id.text.toString()
           val telephone=phone_id.text.toString()
            if(usermail.contentEquals(email)){
            updateprofilePresenter.Updateprofile(name,email,telephone)
            }
            else
            {load()
              Handler().postDelayed({onError("Wrong email")},1500)}
        }
    }
}
