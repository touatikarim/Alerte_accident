package com.example.alertaccident.ui.Vehicule

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.presentation.AddVehiculePresenterImpl
import com.example.alertaccident.presentation.IAddVehiculePresenter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_add__vehicle.*


class Add_Vehicle : Fragment(),AddVehicleView {
   internal lateinit var addVehiculePresenterImpl:IAddVehiculePresenter
    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun load() {
        val progressBar=submit_car
        progressBar?.visibility = View.VISIBLE
        Handler().postDelayed({ progressBar.visibility = View.GONE },1500)
    }

    override fun navigate() {
        load()
        Handler().postDelayed({ findNavController().navigate(R.id.action_add_Vehicle_to_Vehicles,null, Constants.options) }, 1500)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add__vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view,activity!!)
        addVehiculePresenterImpl=AddVehiculePresenterImpl(this)
        addVehiculePresenterImpl.setMainViewContext(activity!!.baseContext)
        UiUtils.setstepper(2,6,stepperTouch_doors,nbr_doors)
        btn_submit.setOnClickListener {
            val matricule=id_matricule.text.toString()
            val marque=id_car.text.toString()
            val assurance= id_assurance.text.toString()
            val couleur=id_color.text.toString()
            val nombrePortes=nbr_doors.text.toString()
            addVehiculePresenterImpl.addVehicule(matricule,marque,assurance,couleur,nombrePortes)
        }

    }
}
