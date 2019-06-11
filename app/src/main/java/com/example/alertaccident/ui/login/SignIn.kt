package com.example.alertaccident.ui.login



import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alertaccident.retrofit.UserManager
import android.widget.Toast

import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.alertaccident.Local.ContactDataSource
import com.example.alertaccident.Local.ContactDatabase
import com.example.alertaccident.presentation.IloginPresenter
import com.example.alertaccident.presentation.LoginPresenterImpl
import com.example.alertaccident.R
import com.example.alertaccident.database.ContactRepository
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.helper.UiUtils.isDeviceConnectedToInternet
import com.example.alertaccident.model.User
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

import es.dmoral.toasty.Toasty
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_sign_in.*



class SignIn : Fragment(),SigninView,GoogleApiClient.OnConnectionFailedListener {


    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d("bett", "onConnectionFailed:" + connectionResult)
    }

    internal lateinit var loginpresnter: IloginPresenter
    lateinit var mGoogleApiClient: GoogleApiClient
    private val RC_SIGN_IN = 9001
    private var compositeDisposable: CompositeDisposable? = null
    private var contactRepository: ContactRepository? = null
//    val options = navOptions {
//        anim {
//            enter = R.anim.slide_in_right
//            exit = R.anim.slide_out_left
//            popEnter = R.anim.slide_in_left
//            popExit = R.anim.slide_out_right
//        }
//    }


    override fun load() {
        val progressBar = login
        progressBar?.setVisibility(View.VISIBLE)
        Handler().postDelayed({ progressBar.setVisibility(View.GONE) }, 1500)

    }

    override fun navigate() {

        load()
        Handler().postDelayed({ findNavController().navigate(R.id.action_signIn_to_home2, null, Constants.options);activity!!.finish() }, 1500)


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
        compositeDisposable = CompositeDisposable()
        val contactDatabase = ContactDatabase.getInstance(activity!!.baseContext)
        contactRepository = ContactRepository.getInstance(ContactDataSource.getInstance(contactDatabase.contactDAO()))
        val disposable = Observable.create(ObservableOnSubscribe<Any>{ e->
            contactRepository!!.insertListContact(Constants.services)
            e.onComplete()
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
            },
                {
                        throwable->Log.d("error",throwable.message)
                },
                {  Log.d("success","emergency list added") })
        compositeDisposable!!.add(disposable)


        btn_login.setOnClickListener {
            val email = id_email.text.toString()
            val password = id_password.text.toString()
            loginpresnter.onLogin(email, password)
            loginpresnter.login(email, password,btn_login)
            btn_login.setEnabled(false)

        }

        btn_login_fb.setOnClickListener {
            if(isDeviceConnectedToInternet(activity!!.baseContext))
            {loginpresnter.signinfb(this) }
            else
                onError(activity!!.baseContext.getString(R.string.no_connection))
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
                if (isDeviceConnectedToInternet(activity!!.baseContext)) {
                    val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
                    startActivityForResult(signInIntent, RC_SIGN_IN)
                } else
                    onError(activity!!.baseContext.getString(R.string.no_connection))

        }


    forget_pass.setOnClickListener {
        findNavController().navigate(R.id.action_signIn_to_forgot_Pass,null,Constants.options)
    }
    back.setOnClickListener {
        activity!!.supportFragmentManager.popBackStack()
    }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       loginpresnter.onActivityResult(requestCode,resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val sp = UserManager.getSharedPref(activity!!.baseContext)
            val user_id=sp.getString("USER_ID","")
            val account = GoogleSignIn.getLastSignedInAccount(activity!!.baseContext)
            val personEmail = account?.email
            val name = account?.displayName
            val imgurl=account?.photoUrl.toString()
            if (name != null && personEmail != null) {
                val user = User(personEmail, "", name, user_id, "")
                UserManager.saveCredentials(activity!!.baseContext, user)
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                val token = result.signInAccount?.idToken.toString()
                UserManager.saveGoogleToken(activity!!.baseContext,token,imgurl)
                if (result.isSuccess) {
                    loginpresnter.registerGoogle(Constants.socialType1,personEmail,token)
                    navigate()
                }
            }
        }


    }

    override fun onDestroyView() {
        compositeDisposable!!.clear()
        super.onDestroyView()
    }
}












