package com.example.alertaccident.ui.login


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.UserManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.alertaccident.presentation.IloginPresenter
import com.example.alertaccident.presentation.LoginPresenterImpl
import com.example.alertaccident.R
import com.example.alertaccident.helper.UiUtils
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_sign_in.*
import java.util.*


class SignIn : Fragment(),SigninView {
    override fun load() {
        val progressBar=login
        progressBar.setVisibility(View.VISIBLE)
        Handler().postDelayed({progressBar.setVisibility(View.GONE)},1500)
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

        Handler().postDelayed({findNavController().navigate(R.id.action_signIn_to_home2,null,options)},1500)


    }

    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }


    internal lateinit var loginpresnter: IloginPresenter
    private var callbackManager: CallbackManager? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view,activity!!)
        loginpresnter = LoginPresenterImpl(this)
        loginpresnter.setMainViewContext(activity!!.baseContext)


        btn_login.setOnClickListener {
            val  email=id_email.text.toString()
            val password=id_password.text.toString()

            loginpresnter.onLogin(email, password)
            loginpresnter.login(email,password)
            val sp =com.example.alertaccident.retrofit.UserManager.getSharedPref(activity!!.baseContext)
            val mail=sp.getString("USER_EMAIL","")





        }


        btn_login_fb.setOnClickListener {
            // Login
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                       findNavController().navigate(R.id.action_signIn_to_home2)
                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")

                    }

                    override fun onError(error: FacebookException) {
                        Log.d("MainActivity", "Facebook onError.")

                    }
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }


}






