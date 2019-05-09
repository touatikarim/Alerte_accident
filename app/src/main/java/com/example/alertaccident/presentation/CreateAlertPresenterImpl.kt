package com.example.alertaccident.presentation


import `in`.galaxyofandroid.spinerdialog.SpinnerDialog

import android.app.Activity
import android.content.Context
import android.net.Uri

import android.view.View
import android.widget.TextView

import com.example.alertaccident.model.AlertModel
import com.example.alertaccident.ui.alertcreation.CreateAlertView
import com.google.firebase.database.FirebaseDatabase
import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.retrofit.UserManager
import nl.dionsegijn.steppertouch.OnStepCallback
import nl.dionsegijn.steppertouch.StepperTouch

import com.example.alertaccident.helper.isAlertValid

import com.google.firebase.storage.FirebaseStorage
import java.io.File


class CreateAlertPresenterImpl(internal var createAlertView: CreateAlertView):IcreateAlertPresenter {



    lateinit var context: Context
    var spinnerdialog: SpinnerDialog? = null
    private var url:String?=null
    private var vid_url:String?=null
    override fun saveAlert(desc: String, user_id: String, service: String, victims: String) {
        val sp = UserManager.getSharedPref(context)
        val email = sp.getString("USER_EMAIL", "")
        val latitude=sp.getString("USER_LAT","")
        val longitude=sp.getString("USER_LNG","")
       // val url= sp.getString("IMAGE_URL","")
        createAlertView.load_alert(View.VISIBLE)

            val ref = FirebaseDatabase.getInstance().getReference("Alerts")
            val alert_id = ref.push().key
            val alert = AlertModel(alert_id, user_id, desc, service, email, victims,latitude,longitude,url,vid_url)
            ref.child(alert_id!!).setValue(alert).addOnCompleteListener {
                createAlertView.load_alert(View.GONE)
                createAlertView.onSuccess(context.getString(R.string.send_alert))
                createAlertView.navigate()
            }
        }








    override fun sendImage(path: String) {
        val storage_ref = FirebaseStorage.getInstance().getReference()
        val file=Uri.fromFile(File(path))
        val image=storage_ref.child("accident/${file.lastPathSegment}")
        val uploadtask=image.putFile(file)
        uploadtask.addOnSuccessListener {
        image.getDownloadUrl().addOnSuccessListener {
               Uri->url=Uri.toString()
                createAlertView.load_image(View.GONE)
                createAlertView.onSuccess("Picture stored successfully")
            //UserManager.saveimageurl(context,uri.toString())

        }
        }


    }
    override fun sendVideo(path: String) {
        val storage_ref = FirebaseStorage.getInstance().getReference()
        val file=Uri.fromFile(File(path))
        val video=storage_ref.child("accident_vids/${file.lastPathSegment}")
        val uploadtask=video.putFile(file)
        uploadtask.addOnSuccessListener {
           video.getDownloadUrl().addOnSuccessListener {
                    Uri->vid_url=Uri.toString()
                createAlertView.load_video(View.GONE)
                createAlertView.onSuccess("Video stored successfully")
                //UserManager.saveimageurl(context,uri.toString())

            }
        }


    }


    override fun setstepper(min: Int, max: Int, stepperTouch: StepperTouch, victims: TextView) {
        stepperTouch.maxValue = max
        stepperTouch.minValue = min
        stepperTouch.sideTapEnabled = true
        stepperTouch.addStepCallback(object : OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                victims.text = value.toString()
            }
        })

    }

    override fun setspinner(service: TextView, activity: Activity) {
        spinnerdialog = SpinnerDialog(activity, Constants.list_of_services,
            "Select Service", R.style.DialogAnimations_SmileWindow)
        spinnerdialog!!.bindOnSpinerListener { item, position ->
            service.setText(Constants.list_of_services[position])
        }
        spinnerdialog!!.showSpinerDialog()
    }

    override fun setMainViewContext(context: Context) {
        this.context = context
    }

    override fun OncreateAlert(desc: String, service: String, victims: String) {
        val isAlertSucces = isAlertValid(desc, service, victims)
        if (isAlertSucces == 0)
            createAlertView.onError(context.getString(R.string.description))
        else if (isAlertSucces == 1)
            createAlertView.onError(context.getString(R.string.noservice))

    }


}