package com.example.alertaccident.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.alertaccident.R
import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.VehiculeModel
import kotlinx.android.synthetic.main.alert_item.view.*
import kotlinx.android.synthetic.main.vehicle_item.view.*

class VehicleAdapter(var items:ArrayList<VehiculeModel>,val context: Context): RecyclerView.Adapter<VehicleAdapter.VehicleHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleHolder {
        return VehicleHolder(LayoutInflater.from(context).inflate(R.layout.vehicle_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VehicleHolder, position: Int) = holder.bind(items[position])


    class VehicleHolder(view: View) : RecyclerView.ViewHolder(view) {

        val immatricule=view.car_name
        val name=view.registration_number




        @SuppressLint("SetTextI18n")
        fun bind(vehicle: VehiculeModel) = with(itemView) {
            immatricule.text=" "+vehicle.immatricule
            name.text=" "+vehicle.marque

            if(!vehicle.images.isNullOrEmpty()) {
                    Glide.with(context).load(vehicle.images).listener(object :RequestListener<Drawable>{
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            load_image_car.visibility=View.GONE
                            return false

                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            load_image_car.visibility=View.GONE
                            return false
                        }

                    })
                        .into(car_pic)
            }




        }

    }
}