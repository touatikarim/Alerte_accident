package com.example.alertaccident.ui.register

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.alertaccident.presentation.IregisterPresenter
import com.example.alertaccident.presentation.RegisterPresenterImpl
import com.example.alertaccident.R
import com.example.alertaccident.helper.OnBackPressedListener
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.helper.isRegistrationValid
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUp : Fragment(),SignupView, OnBackPressedListener {
    override fun onBackPressed() {
        activity!!.supportFragmentManager.popBackStack()
    }

    override fun load() {
        val progressBar = submit
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
        Handler().postDelayed({
            findNavController().navigate(R.id.action_signUp_to_signIn, null, options)
        }, 1500)
    }



    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext,message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext,message, Toast.LENGTH_SHORT).show()
    }

    internal lateinit var registerpresnter:IregisterPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view,activity!!)
        registerpresnter=RegisterPresenterImpl(this)
       registerpresnter.setMainViewContext(activity!!.baseContext)

        btn_submit.setOnClickListener {
            val nom = id_name.text.toString()
            val email = id_email.text.toString()
            val password = id_password.text.toString()
            val telephone = id_phone.text.toString()
            val cin = id_CIN.text.toString()
            val repeatpassword = id_confirm_password.text.toString()
            if(isRegistrationValid(email, password, repeatpassword, nom, telephone,cin)==-1)
            { registerpresnter.Register(nom, email, password, telephone, cin)}
            else
                registerpresnter.onRegister(email, password, repeatpassword, nom, telephone,cin)

        }
        back_signup.setOnClickListener {
            onBackPressed()
        }
    }
}
