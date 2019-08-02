package com.example.alertaccident.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import com.example.alertaccident.R
import com.example.alertaccident.retrofit.UserManager
import kotlinx.android.synthetic.main.alert_item.view.*
import kotlinx.android.synthetic.main.fragment_accident_pic.*


class AccidentPic : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accident_pic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageurl=UserManager.getAlertPic(activity!!.baseContext)
        Log.e("url----",imageurl)
        if(!imageurl.isNullOrEmpty()){
            Glide.with(activity!!.baseContext).load(imageurl).listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    load_full_image.visibility=View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    load_full_image.visibility=View.GONE
                    return false
                }

            })
                .into(full_accident_pic)
        }
        else
        {
            load_full_image.visibility=View.GONE
            Glide.with(activity!!.baseContext).load(R.drawable.no_picture_holder).into(full_accident_pic)
        }
    }
}
