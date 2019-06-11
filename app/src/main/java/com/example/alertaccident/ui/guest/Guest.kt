package com.example.alertaccident.ui.guest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.alertaccident.R


import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.GPSUtils
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.presentation.GuestPresenterImpl
import com.example.alertaccident.presentation.IGuestPresenter
import com.example.alertaccident.retrofit.UserManager
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_create_alert.*
import kotlinx.android.synthetic.main.fragment_guest.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*





class Guest : Fragment(),GuestView {



    lateinit  var imageFilePath: String
    lateinit var guestPresenter:IGuestPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view, activity!!)
        guestPresenter=GuestPresenterImpl(this)
        guestPresenter.setMainViewContext(activity!!.baseContext)
        guestPresenter.getLocation(activity!!)
        UiUtils.setstepper(Constants.min_value, Constants.max_value, stepperTouch_guest, nbr_guest)



        btn_send_guest.setOnClickListener {
            val victims = nbr_guest.text.toString()
            val description = id_description_guest.text.toString()
            val service = service_chosen_guest.text.toString()
            if (UiUtils.isDeviceConnectedToInternet(activity!!.baseContext)) {
                guestPresenter.getLocation(activity!!)
               Handler().postDelayed( {guestPresenter.saveAlert(description,service,victims,activity!!)},2)
                UserManager.clearSharedPref(activity!!.baseContext)
            }
        }
        service_chosen_guest.setOnClickListener {
            UiUtils.setspinner(service_chosen_guest, activity!!)
        }
        accident_photo_guest.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                    takePictureintent ->  takePictureintent.resolveActivity(activity!!.packageManager)?.also {
                val photoFile: File?=try {
                    createImageFile()
                }catch (ex: IOException){
                    null
                }
                photoFile?.also {
                    val photoUri=
                        FileProvider.getUriForFile(activity!!.baseContext,"com.example.alertaccident.fileprovider", it)
                    takePictureintent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
                    startActivityForResult(takePictureintent, Constants.REQUEST_IMAGE_CAPTURE)

                }
            }

            }


        }
        back_guest.setOnClickListener {
            UserManager.clearSharedPref(activity!!.baseContext)
            activity!!.supportFragmentManager.popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Constants.REQUEST_IMAGE_CAPTURE && resultCode== Activity.RESULT_OK) {
            photo_taken_guest.setVisibility(View.VISIBLE)
            Glide.with(activity!!.baseContext).load(imageFilePath).into(photo_taken_guest)
            load_image(View.VISIBLE)
            guestPresenter.sendImage(imageFilePath)

        }
        else {
            onError("operation canceled")
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        ).apply {
            imageFilePath=absolutePath
        }

    }
    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun load_image(state: Int) {
        val progressBar=store_image_guest
        progressBar?.visibility=state
    }

    override fun load_alert(state: Int) {
        val progressBar=send_alert_guest
        progressBar?.visibility=state
    }

    override fun navigate() {
        findNavController().navigate(R.id.action_guest_to_userGuest,null,Constants.options)
    }

}
