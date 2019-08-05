package com.example.alertaccident.ui.alerts

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.retrofit.UserManager
import kotlinx.android.synthetic.main.fragment_notif_details.*


class NotifDetails : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notif_details, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sp = UserManager.getSharedPref(activity!!.baseContext)
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
        date_notif.text=" "+date
        location_notif.text=" "+location
        nbr_victims.text=" "+victims+" victims"
        map_notif.setOnClickListener {
            findNavController().navigate(R.id.action_notifDetails_to_map2,null,Constants.options)

        }

    }
}
