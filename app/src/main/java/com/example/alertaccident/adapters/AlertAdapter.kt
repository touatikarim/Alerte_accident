package com.example.alertaccident.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.alertaccident.R
import com.example.alertaccident.model.AlertModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alert_item.view.*

class AlertAdapter(var items:ArrayList<AlertModel>,val context: Context):RecyclerView.Adapter<AlertAdapter.AlertHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertHolder {
        return AlertHolder(LayoutInflater.from(context).inflate(R.layout.alert_item,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AlertHolder, position: Int) = holder.bind(items[position])




    class AlertHolder(view:View):RecyclerView.ViewHolder(view) {
        val alert_title=view.user_email
        val sender=view.sender_email
        val service=view.service_contacté
        val victim=view.victims
        val description=view.desc
        val pic=view.accident_pic


        @SuppressLint("SetTextI18n")
        fun bind(alert:AlertModel)=with(itemView){
            alert_title.text=context.getString(R.string.email_alert)+alert.email
            sender.text=context.getString(R.string.name_alert)+alert.desc
            service.text=context.getString(R.string.service_alert)+alert.service
            victim.text=context.getString(R.string.victims_alert)+alert.victims
            description.text=context.getString(R.string.desc_alert)+alert.desc
            Picasso.with(context).load(alert.imageurl).into(pic)

        }

    }
}


