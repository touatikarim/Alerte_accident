package com.example.alertaccident.ui.alertcreation





import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


import com.example.alertaccident.R
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.presentation.CreateAlertPresenterImpl
import com.example.alertaccident.presentation.IcreateAlertPresenter
import com.example.alertaccident.retrofit.UserManager
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_create_alert.*
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.isAlertValid


import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CreateAlert : Fragment(),CreateAlertView {



    lateinit  var imageFilePath: String
    lateinit var videoFilePath:String
    lateinit var alertpresenter: IcreateAlertPresenter
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
        return inflater.inflate(R.layout.fragment_create_alert, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view, activity!!)
        alertpresenter = CreateAlertPresenterImpl(this)
        alertpresenter.setMainViewContext(activity!!.baseContext)

        alertpresenter.setstepper(Constants.min_value, Constants.max_value, stepperTouch, nbr)
        val sp = UserManager.getSharedPref(activity!!.baseContext)
        val user_id = sp.getString("USER_ID", "")
        Log.d("user_id",user_id)
        btn_send.setOnClickListener {


            val victims = nbr.text.toString()
            val description = id_description.text.toString()
            val service = service_chosen.text.toString()
            if (UiUtils.isDeviceConnectedToInternet(activity!!.baseContext)) {
                if (isAlertValid(description, service, victims) == -1) {

                    alertpresenter.saveAlert(description, user_id, service, victims)

                } else
                    alertpresenter.OncreateAlert(description, service, victims)
            } else {

                onError(activity!!.baseContext.getString(R.string.no_connection))
            }
        }
        service_chosen.setOnClickListener {
            alertpresenter.setspinner(service_chosen, activity!!)
        }
        accident_photo.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                takePictureintent ->  takePictureintent.resolveActivity(activity!!.packageManager)?.also {
                val photoFile:File?=try {
                    createImageFile()
                }catch (ex:IOException){
                    null
                }
               photoFile?.also {
                   val photoUri=FileProvider.getUriForFile(activity!!.baseContext,"com.example.alertaccident.fileprovider", it)
                   takePictureintent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
                   startActivityForResult(takePictureintent,Constants.REQUEST_IMAGE_CAPTURE)

               }
            }

            }


        }
        accident_video.setOnClickListener {
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also {
                    takeVideoIntent ->
                takeVideoIntent.resolveActivity(activity!!.packageManager)?.also {
                    val videoFile: File? = try {
                        createVideoFile()
                    } catch (ex: IOException) {
                        null
                    }
                    videoFile?.also{
                        val videoUri=FileProvider.getUriForFile(activity!!.baseContext,"com.example.alertaccident.fileprovider", it)
                        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT,videoUri)
                        startActivityForResult(takeVideoIntent, Constants.REQUEST_VIDEO_CAPTURE)
                    }

                }
            }
        }



    }


    override fun load_image(state: Int) {
        val progressBar=store_image
        progressBar?.visibility=state
    }
    override fun load_alert(state: Int) {
        val progressBar=send_alert
        progressBar?.visibility=state
    }
    override fun load_video(state: Int) {
        val progressBar=store_video
        progressBar?.visibility=state
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Constants.REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK) {
            photo_taken.setVisibility(View.VISIBLE)
            Glide.with(activity!!.baseContext).load(imageFilePath).into(photo_taken)
            load_image(View.VISIBLE)
            alertpresenter.sendImage(imageFilePath)

        }
        else if(requestCode==Constants.REQUEST_VIDEO_CAPTURE && resultCode== RESULT_OK){
            video_taken.setVisibility(View.VISIBLE)
            val videoUri: Uri =data!!.data
            video_taken.setVideoURI(videoUri)
            load_video(View.VISIBLE)
            alertpresenter.sendVideo(videoFilePath)

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
    @Throws(IOException::class)
    private fun createVideoFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val videoFileName = "VID_" + timeStamp + "_"
        val storageDir = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            videoFileName, /* prefix */
            ".mp4", /* suffix */
            storageDir      /* directory */
        ).apply {
            videoFilePath=absolutePath
        }

    }
}

