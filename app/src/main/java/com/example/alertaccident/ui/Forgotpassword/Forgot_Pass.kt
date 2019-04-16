package com.example.alertaccident.ui.Forgotpassword

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

import com.example.alertaccident.R
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.presentation.ForgotPassPresnterImpl
import com.example.alertaccident.presentation.IForgotPassPresenter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_forgot__pass.*
import kotlinx.android.synthetic.main.fragment_sign_in.*


class Forgot_Pass : Fragment(),ForgotPassView {


    override fun load() {
        val progressBar = forgot
        progressBar.setVisibility(View.VISIBLE)
        Handler().postDelayed({ progressBar.setVisibility(View.GONE) }, 1500)
    }
    override fun navigate() {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        load()
        Handler().postDelayed({ findNavController().navigate(R.id.action_forgot_Pass_to_signIn, null, options) }, 1500)
    }

    lateinit var forgetpasspresenter:IForgotPassPresenter


    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot__pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view, activity!!)
        forgetpasspresenter=ForgotPassPresnterImpl(this)
        forgetpasspresenter.setMainViewContext(activity!!.baseContext)

        btn_sub.setOnClickListener {
            val email=id_fogot_email.text.toString()
            forgetpasspresenter.forgetpass(email)
        }
    }
}
