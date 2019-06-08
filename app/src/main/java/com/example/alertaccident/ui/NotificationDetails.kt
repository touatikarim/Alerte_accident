package com.example.alertaccident.ui

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.alertaccident.R
import com.example.alertaccident.retrofit.UserManager
import kotlinx.android.synthetic.main.activity_notification_details.*
import kotlinx.android.synthetic.main.alert_item.view.*

class NotificationDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_details)
        val sp = UserManager.getSharedPref(this)
        val location=sp.getString("NOTIF_LOCATION","")
        val imageurl=sp.getString("NOTIF_IMAGE","")
        val date=sp.getString("NOTIF_DATE","")
        val victims=sp.getString("NOTIF_Victims","")
        val description=sp.getString("NOTIF_DESC","")
        Glide.with(this).load(imageurl)
            .listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    load_image.visibility= View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    load_image.visibility= View.GONE
                    return false
                }

            })
            .into(accident_pic)
       desc.text=description
       date_text.text=date
        location_id.text=location
       victims_text.text=victims
//        check_map.setOnClickListener {
//
//        }

    }
}
