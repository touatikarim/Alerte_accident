package com.example.alertaccident.ui.login


import android.accounts.Account
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler

import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alertaccident.retrofit.UserManager
import android.widget.EditText

import android.widget.Toast
import androidx.fragment.app.FragmentActivity

import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.alertaccident.presentation.IloginPresenter
import com.example.alertaccident.presentation.LoginPresenterImpl
import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.model.User
import com.facebook.CallbackManager
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInApi
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.navigation.NavigationView

import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_sign_in.*
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import java.io.IOException


class SignIn : Fragment(),SigninView,GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d("bett", "onConnectionFailed:" + connectionResult)
    }

    internal lateinit var loginpresnter: IloginPresenter
    lateinit var mGoogleApiClient: GoogleApiClient
    private val RC_SIGN_IN = 9001
    val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }


    override fun load() {
        val progressBar = login
        progressBar.setVisibility(View.VISIBLE)
        Handler().postDelayed({ progressBar.setVisibility(View.GONE) }, 1500)
    }

    override fun navigate() {

        load()
        Handler().postDelayed({ findNavController().navigate(R.id.action_signIn_to_home2, null, options) }, 1500)


    }

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
        return inflater.inflate(R.layout.fragment_sign_in, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view, activity!!)
        loginpresnter = LoginPresenterImpl(this)
        loginpresnter.setMainViewContext(activity!!.baseContext)


        btn_login.setOnClickListener {
            val email = id_email.text.toString()
            val password = id_password.text.toString()

            loginpresnter.onLogin(email, password)
            loginpresnter.login(email, password)
            val sp = UserManager.getSharedPref(activity!!.baseContext)
            val mail = sp.getString("USER_EMAIL", "")

        }

        btn_login_fb.setOnClickListener {
            loginpresnter.signinfb(this)
        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestIdToken(Constants.clientid)
            .requestEmail()
            .build()

        mGoogleApiClient = GoogleApiClient.Builder(activity!!.baseContext)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
             mGoogleApiClient.connect()

        btn_login_google.setOnClickListener {

            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)

        }

    forget_pass.setOnClickListener {
        findNavController().navigate(R.id.action_signIn_to_forgot_Pass,null,options)
    }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       loginpresnter.onActivityResult(requestCode,resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val account=GoogleSignIn.getLastSignedInAccount(activity!!.baseContext)
            val personEmail = account?.email
            val name=account?.displayName
            val user=User(personEmail!!,"",name!!,"")
            UserManager.saveCredentials(activity!!.baseContext,user)
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            val token=result.signInAccount?.idToken.toString()
            Log.d("tokennnn",token)
            UserManager.saveGoogleToken(activity!!.baseContext,token)
            if (result.isSuccess)
                navigate()
        }


    }



}












