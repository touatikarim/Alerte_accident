package com.example.alertaccident.presentation

import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.app.Activity
import android.content.Context
import android.os.Handler
import com.example.alertaccident.model.AlertModel
import com.example.alertaccident.ui.alertcreation.CreateAlertView
import com.google.firebase.database.FirebaseDatabase
import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.retrofit.UserManager

class CreateAlertPresenterImpl(internal var createAlertView: CreateAlertView):IcreateAlertPresenter {

    lateinit var context: Context




    override fun saveAlert(desc: String,user_id:String,service:String)
    {
        val sp = UserManager.getSharedPref(context)
        val email=sp.getString("USER_EMAIL","")
        val ref=FirebaseDatabase.getInstance().getReference("Alerts")
        val alert_id=ref.push().key

        val alert=AlertModel(alert_id,user_id, desc,service,email)

        ref.child(alert_id!!).setValue(alert).addOnCompleteListener {
            createAlertView.load()
            Handler().postDelayed({createAlertView.onSuccess(context.getString(R.string.send_alert))},1500)
        }
    }

    override fun setMainViewContext(context: Context) {
        this.context=context
    }



}