package com.example.alertaccident.ui.Vehicule

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.presentation.AddVehiculePresenterImpl
import com.example.alertaccident.presentation.IAddVehiculePresenter
import com.example.alertaccident.retrofit.UserManager
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_add__vehicle.*
import kotlinx.android.synthetic.main.fragment_create_alert.*
import kotlinx.android.synthetic.main.nav_header.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class Add_Vehicle : Fragment(),AddVehicleView {
    lateinit  var imageFilePath: String
   internal lateinit var addVehiculePresenterImpl:IAddVehiculePresenter
    override fun load_image(state: Int) {
        val progressBar=store_image_car
        progressBar?.visibility=state
    }

    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun load() {
        val progressBar=submit_car
        progressBar?.visibility = View.VISIBLE
        Handler().postDelayed({ progressBar.visibility = View.GONE },1500)
    }

    override fun navigate() {
        load()
        Handler().postDelayed({ findNavController().navigate(R.id.action_add_Vehicle_to_Vehicles,null, Constants.options) }, 1500)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add__vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view,activity!!)
        addVehiculePresenterImpl=AddVehiculePresenterImpl(this)
        addVehiculePresenterImpl.setMainViewContext(activity!!.baseContext)
        UiUtils.setstepper(2,6,stepperTouch_doors,nbr_doors)
        val sp = UserManager.getSharedPref(activity!!.baseContext)
        val user_id=sp.getString("USER_ID","")
        btn_submit.setOnClickListener {
            val matricule=id_matricule.text.toString()
            val marque=id_car.text.toString()
            val assurance= id_assurance.text.toString()
            val couleur=id_color.text.toString()
            val nombrePortes=nbr_doors.text.toString()
            val images=url.text.toString()

            addVehiculePresenterImpl.addVehicule(matricule,marque,assurance,couleur,nombrePortes,user_id)
        }
        car_photo.setOnClickListener {
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
                    startActivityForResult(takePictureintent,Constants.REQUEST_IMAGE_CAPTURE)

                }
            }

            }


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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Constants.REQUEST_IMAGE_CAPTURE && resultCode== Activity.RESULT_OK) {
            photo_taken_car.setVisibility(View.VISIBLE)
            Glide.with(activity!!.baseContext).load(imageFilePath).into(photo_taken_car)
            load_image(View.VISIBLE)
            addVehiculePresenterImpl.sendImage(imageFilePath)

        }

        else {
            onError("operation canceled")
        }


    }
}
