package com.example.alertaccident.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alertaccident.R
import com.example.alertaccident.model.ReportModel
import kotlinx.android.synthetic.main.report_item.view.*

class ReportAdapter(var items:ArrayList<ReportModel>,val context: Context):RecyclerView.Adapter<ReportAdapter.ReportHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportHolder {
        return ReportHolder(LayoutInflater.from(context).inflate(R.layout.report_item,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ReportHolder, position: Int)=holder.bind(items[position])




    class ReportHolder(view:View):RecyclerView.ViewHolder(view) {
            val name_car=view.car_name
            val localisation=view.location_id
            val date_acc=view.date





        fun bind(report:ReportModel)=with(itemView){
            name_car.text=report.marqueA
            localisation.text=report.localisation
            date_acc.text=report.date

        }

    }
}