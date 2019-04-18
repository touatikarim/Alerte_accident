package com.example.alertaccident.presentation

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.UpdateModel
import com.example.alertaccident.model.User
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.Connexion
import com.example.alertaccident.ui.home.Home
import com.example.alertaccident.ui.update.UpdateprofileView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Handler

class UpdateProfilePresenterImpl(internal var updateprofileView: UpdateprofileView):IUpdateProfilePresenter {

    lateinit  var context: Context
    override fun Updateprofile(nom: String, email: String, telephone: String) {
        val options= ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_left, R.anim.slide_out_right)
        val intent = Intent(context, Home::class.java)
        val updateModel=UpdateModel(nom,telephone)
        val sp= UserManager.getSharedPref(context)
        val usermail=sp.getString("USER_EMAIL","")
        RetrofitManager.getInstance(Constants.baseurl).service!!.updateuser(usermail,updateModel)
            .enqueue(object :Callback<ApiResponse>{

                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    updateprofileView.load()
                    android.os.Handler().postDelayed({ updateprofileView.onSuccess(response.body()!!.message)}, 1500)

                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    updateprofileView.onError(t.message!!)
                }

            })


    }

    override fun setMainViewContext(context: Context) {
        this.context=context
    }

}