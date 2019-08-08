package com.example.alertaccident.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.alertaccident.R
import com.example.alertaccident.model.VehiculeModel
import com.example.alertaccident.retrofit.UserManager
import kotlinx.android.synthetic.main.vehicule_report_item.view.*

class VehicleAccidentAdapter(var items:ArrayList<VehiculeModel>, val context: Context): RecyclerView.Adapter<VehicleAccidentAdapter.VehicleAccidentHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleAccidentAdapter.VehicleAccidentHolder {
        return VehicleAccidentHolder(LayoutInflater.from(context).inflate(R.layout.vehicule_report_item,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VehicleAccidentAdapter.VehicleAccidentHolder, position: Int) = holder.bind(items[position])
    class VehicleAccidentHolder(view: View) : RecyclerView.ViewHolder(view) {

        val matricule=view.car_number
//        val color=view.car_color
//        val assurance=view.car_insurance
//        val name=view.car_name





        @SuppressLint("SetTextI18n")
        fun bind(vehicle: VehiculeModel) = with(itemView) {
            matricule.text=" "+vehicle.immatricule
           val color=vehicle.couleur
            val assurance=vehicle.assurance
            val name=vehicle.marque
            val number=vehicle.immatricule
            if(!vehicle.images.isNullOrEmpty()) {
                Glide.with(context).load(vehicle.images)
                    .into(car_pic)
            }
            checkbox_meat.setOnCheckedChangeListener { compoundButton, isChecked  ->
                if(isChecked){
                    UserManager.saveVehicle(context,color,assurance,name,number)
                }

            }




        }

    }

}