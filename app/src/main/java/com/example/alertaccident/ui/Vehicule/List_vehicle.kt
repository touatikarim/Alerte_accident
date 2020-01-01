package com.example.alertaccident.ui.Vehicule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.presentation.IListeVehiculePresenter
import com.example.alertaccident.presentation.ListVehiculePresenterImpl
import kotlinx.android.synthetic.main.fragment_list_vehicle.*


class List_vehicle : Fragment(),ListvehicleView {

   internal lateinit var listvehiclepresenter:IListeVehiculePresenter

    override fun load(stat: Int) {
        val progressbar=load_vehicles
        progressbar?.visibility=stat
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listvehiclepresenter=ListVehiculePresenterImpl(this)
        listvehiclepresenter.setMainViewContext(activity!!.baseContext)
        load(View.VISIBLE)
        listvehiclepresenter.getListVehicule(recyclerView_cars,empty_recycler)


        fab_add.setOnClickListener {
            findNavController().navigate(R.id.action_list_vehicle_to_add_Vehicle,null,Constants.options)
        }
    }
}
