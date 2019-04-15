package com.example.alertaccident.presentation




import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Handler
import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.isDataValid
import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.LoginModel
import com.example.alertaccident.model.User
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.login.SigninView
import com.google.gson.JsonParser

import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.alertaccident.ui.Connexion

import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import retrofit2.*
import java.util.*


class LoginPresenterImpl(internal var signinview:SigninView):IloginPresenter
     {
    private var callbackManager: CallbackManager? = null
    lateinit  var context: Context
    override fun login(email:String,password:String) {

        val loginModel = LoginModel(email, password)



        if (isDataValid(email, password) == -1) {
            RetrofitManager.getInstance(Constants.baseurl).service!!.loginuser(loginModel)
                .enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>?) {

                        if (response != null) {

                            if (response.code() == 200) {
                                val id=response.body()!!.data._id
                                val name=response.body()!!.data.nom
                                val user = User(email,password,name,id)
                                UserManager.saveCredentials(context,user)
                                signinview.navigate()
                                Handler().postDelayed({ signinview.onSuccess(response.body()!!.message) }, 1500)

                            } else {
                                val errorJsonString = response.errorBody()?.string()
                                val message = JsonParser().parse(errorJsonString)
                                    .asJsonObject["message"]
                                    .asString
                                signinview.load()
                                    Handler().postDelayed({ signinview.onError(message) }, 1500)

                                // Handler().postDelayed({ signinview.onError(message) }, 1500)
                                //response.raw().message())

                            }
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        signinview.onError(t.message!!)//context.getString(R.string.Server))
                    }
                })

       }
    }


    override fun onLogin(email: String, password: String) {
        val isLoginsucces= isDataValid(email,password)
        if (isLoginsucces==0)
            signinview.onError(context.getString(R.string.email_address))
        else if (isLoginsucces==1)
            signinview.onError(context.getString(R.string.valid_address))
        else if(isLoginsucces==2)
            signinview.onError(context.getString(R.string.valid_password))


}
    override fun setMainViewContext(context: Context) {
        this.context=context
    }

    override fun signinfb(fragment: Fragment) {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logInWithReadPermissions(fragment, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d("token", "Facebook token: " + loginResult.accessToken.token)
                    signinview.navigate()

                }
                override fun onCancel() {
                    Log.d("msg", "Facebook onCancel.")

                }
                override fun onError(error: FacebookException) {
                    Log.d("msg", "Facebook onError.")

                }
            })

    }

    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)

    }



}