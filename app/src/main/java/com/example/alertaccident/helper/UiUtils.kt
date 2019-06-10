package com.example.alertaccident.helper

import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.app.*
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.widget.TextView
import nl.dionsegijn.steppertouch.OnStepCallback
import nl.dionsegijn.steppertouch.StepperTouch
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import java.net.HttpURLConnection
import java.net.URL
import com.example.alertaccident.R
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.NotificationDetails


object UiUtils {
    //private var spinnerdialog: SpinnerDialog? = null
    private var notificationManager:NotificationManager?=null
    fun hideKeyboardOntouch(view: View?, activity: Activity) {

           if (view !is EditText && view != null) {
               view.setOnTouchListener(object : View.OnTouchListener {
                   override fun onTouch(v: View, event: MotionEvent): Boolean {
                       try{
                       UiUtils.hideKeyboardByElement(activity)}
                       catch (e: Exception) {
                           Log.d("error", e.message)
                       }
                       return false
                   }
               })
           }
           //If a layout container, iterate over children and seed recursion.
           if (view is ViewGroup) {
               for (i in 0 until (view as ViewGroup).childCount) {
                   val innerView = (view as ViewGroup).getChildAt(i)
                   hideKeyboardOntouch(innerView, activity)
               }
           }

    }


    private fun hideKeyboardByElement(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        try {
            inputMethodManager!!.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )
        } catch (e: Exception) {
            Log.d("heree", e.message)
        }

    }


    fun isDeviceConnectedToInternet(context: Context) : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return (activeNetwork != null)
    }

    fun showAlert(activity: Activity, context: Context) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(context.getString(R.string.no_internet))
            .setMessage(context.getString(R.string.no_internet_msg))
            .setPositiveButton(context.getString(R.string.ok)) { paramDialogInterface, paramInt -> activity.finish()
            }

        dialog.show()
    }
    fun setstepper(min: Int, max: Int, stepperTouch: StepperTouch, victims: TextView) {
        stepperTouch.maxValue = max
        stepperTouch.minValue = min
        stepperTouch.sideTapEnabled = true
        stepperTouch.addStepCallback(object : OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                victims.text = value.toString()
            }
        })

    }

    fun setspinner(service: TextView, activity: Activity) {
        val spinnerdialog = SpinnerDialog(activity, Constants.list_of_services,
            "Select Service", R.style.DialogAnimations_SmileWindow)
        spinnerdialog.bindOnSpinerListener { item, position ->
            service.setText(Constants.list_of_services[position])
        }
        spinnerdialog.showSpinerDialog()
    }

    fun createNotificationChannel(context: Context) {
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_LOW
        } else {
            2
        }
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(Constants.NOTIF_CHANNEL_ID, Constants.APP_NAME, importance)
        } else {
            Log.d("here", "sdk < 24")
            return
        }
        channel.enableLights(true)
        channel.lightColor = Color.RED

        channel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        channel.enableVibration(true)
        notificationManager?.createNotificationChannel(channel)

    }
    fun sendNotification(context: Context, title: String, description: String,image:Bitmap,alertId:String?) {
        val sp = UserManager.getSharedPref(context)
        val last_alert = sp.getString("LAST_ALERT_ID", "")
        if(alertId!=last_alert) {
            if (notificationManager != null) {
                val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val resultIntent = Intent(context, NotificationDetails::class.java)
                    val pendingIntent =
                        PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                    val icon = Icon.createWithResource(context, android.R.drawable.ic_dialog_info)
                    val action: Notification.Action =
                        Notification.Action.Builder(icon, "Open", pendingIntent).build()
                    Notification.Builder(context, Constants.NOTIF_CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(description)
                        .setSmallIcon(R.mipmap.mobelite_round)
                        .setChannelId(Constants.NOTIF_CHANNEL_ID)
                        .setContentIntent(pendingIntent)
                        .setActions(action)
                        .setStyle(
                            Notification.BigPictureStyle()
                                .bigPicture(image)
                        )
                        .build()
                } else {
                    Log.d("here", "sdk < 24")
                    return
                }

                notificationManager?.notify(Constants.NOTIFICATION_ID, notification)
            } else {
                createNotificationChannel(context)
                sendNotification(context, title, description, image,alertId)
            }
        }
    }

    fun getBitmapfromUrl(imageUrl: String): Bitmap? {
        try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input = connection.getInputStream()
            return BitmapFactory.decodeStream(input)

        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            return null

        }

    }

}













