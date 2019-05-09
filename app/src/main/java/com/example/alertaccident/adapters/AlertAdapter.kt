package com.example.alertaccident.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.alertaccident.R
import com.example.alertaccident.model.AlertModel
import com.example.alertaccident.retrofit.UserManager
import kotlinx.android.synthetic.main.alert_item.view.*

class AlertAdapter(var items:ArrayList<AlertModel>,val context: Context):RecyclerView.Adapter<AlertAdapter.AlertHolder>() {

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
        val alert_title=view.user_email
        val sender=view.sender_email
        val service=view.service_contacté
        val victim=view.victims
        val description=view.desc
        val pic=view.accident_pic
        val lat=view.lat
        val lng=view.lng


        @SuppressLint("SetTextI18n")
        fun bind(alert:AlertModel)=with(itemView){
            val options = navOptions {
                anim {
                    enter = R.anim.slide_in_right
                    exit = R.anim.slide_out_left
                    popEnter = R.anim.slide_in_left
                    popExit = R.anim.slide_out_right
                }
            }
            alert_title.text=context.getString(R.string.email_alert)+alert.email
            sender.text=context.getString(R.string.name_alert)+alert.desc
            service.text=context.getString(R.string.service_alert)+alert.service
            victim.text=context.getString(R.string.victims_alert)+alert.victims
            description.text=context.getString(R.string.desc_alert)+alert.desc
            lat.text=alert.latitude
            lng.text=alert.longitude
            if(!alert.imageurl.isNullOrEmpty()) {
               Glide.with(context).load(alert.imageurl).into(pic)
            }
            check_map.setOnClickListener {
                UserManager.savealertlocation(lat.text.toString(),lng.text.toString(),context)
               findNavController().navigate(R.id.action_alerts_dest_to_map_dest,null,options)
            }
        }

    }
}


