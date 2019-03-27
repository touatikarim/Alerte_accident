package com.example.alertaccident.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.alertaccident.presentation.IloginPresenter
import com.example.alertaccident.presentation.LoginPresenterImpl
import com.example.alertaccident.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_sign_in.*



class SignIn : Fragment(),SigninView {
    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }


    internal lateinit var loginpresnter: IloginPresenter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginpresnter = LoginPresenterImpl(this)
       val respo= loginpresnter.observe().value

        btn_login.setOnClickListener {
            val  email=id_email.text.toString()
            val password=id_password.text.toString()
            loginpresnter.onLogin(email, password)
            loginpresnter.login(email,password)

        }

    }
}





