package com.example.alertaccident.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.model.AlertModel
import com.example.alertaccident.retrofit.UserManager
import kotlinx.android.synthetic.main.alert_item.view.*

class AlertAdapter(var items:ArrayList<AlertModel>,val context: Context):RecyclerView.Adapter<AlertAdapter.AlertHolder>() {
    var circularProgressDrawable = CircularProgressDrawable(context).start()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertHolder {
        return AlertHolder(LayoutInflater.from(context).inflate(R.layout.alert_item,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AlertHolder, position: Int) = holder.bind(items[position])

    override fun getItemId(position: Int): Long {
        return items[position].alert_id.hashCode().toLong()
    }

    class AlertHolder(view:View):RecyclerView.ViewHolder(view) {
        //val alert_title=view.user_email
        val sender=view.sender_email
        val service=view.service_contacté
        val victim=view.victims
        val description=view.desc
        val pic=view.accident_pic
        val date=view.date
        val location=view.location_id
        val lat=view.lat
        val lng=view.lng


        @SuppressLint("SetTextI18n")
        fun bind(alert:AlertModel)=with(itemView){
            sender.text=context.getString(R.string.email_alert)+alert.email
            if (alert.email.isEmpty()){
                sender.text=context.getString(R.string.email_alert)+"Unregistred user"
            }
            service.text=context.getString(R.string.service_alert)+alert.service
            victim.text=context.getString(R.string.victims_alert)+alert.victims
            description.text=context.getString(R.string.desc_alert)+alert.desc
            lat.text=alert.latitude
            lng.text=alert.longitude
            date.text=context.getString(R.string.date)+alert.date
            location.text=context.getString(R.string.accident_location)+alert.location
            if(!alert.imageurl.isNullOrEmpty()) {

               Glide.with(context).load(alert.imageurl)
                   .listener(object:RequestListener<Drawable>{
                       override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                           load_image.visibility=View.GONE
                           return false
                       }

                       override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            load_image.visibility=View.GONE
                           return false
                       }

                   })
                   .into(pic)
            }
            else {
                load_image.visibility=View.GONE
                Glide.with(context).load(R.drawable.no_picture_holder).into(pic)
            }

            check_map.setOnClickListener {
                UserManager.savealertlocation(lat.text.toString(),lng.text.toString(),context)
               findNavController().navigate(R.id.action_alerts_dest_to_map_dest,null, Constants.options)
            }
            accident_pic.setOnClickListener {
                UserManager.setAlertPic(alert.imageurl,context)
                findNavController().navigate(R.id.action_alerts_dest_to_accidentPic,null,Constants.options)
            }
        }

    }
}


