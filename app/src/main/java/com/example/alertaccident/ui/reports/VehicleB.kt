package com.example.alertaccident.ui.reports

import android.content.Context
import android.net.Uri
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
import com.example.alertaccident.presentation.IVehiculeBPresenter
import com.example.alertaccident.presentation.VehiculeBPresenterImpl
import com.example.alertaccident.retrofit.UserManager
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_vehicle_b.*
import java.text.SimpleDateFormat
import java.util.*


class VehicleB : Fragment(),VehicleBView {

    internal lateinit var vehicleB:IVehiculeBPresenter


    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun load() {
        val progressBar=report
        progressBar?.visibility = View.VISIBLE
        Handler().postDelayed({ progressBar.visibility = View.GONE },1500)
    }

    override fun navigate() {
        load()
        Handler().postDelayed({ findNavController().navigate(R.id.action_vehicleB_to_accident_Reports,null, Constants.options) }, 1500)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view,activity!!)
        vehicleB=VehiculeBPresenterImpl(this)
        vehicleB.setMainViewContext(activity!!.baseContext)
        val sp = UserManager.getSharedPref(activity!!.baseContext)
        val nameA=sp.getString("USER_NAME","")
        val localisation=sp.getString("USER_COUNTRY","")+sp.getString("USER_CITY","")+sp.getString("USER_AREA","")
        val marqueA=sp.getString("CAR_NUMBER","")
        val assuranceA=sp.getString("CAR_INSURANCE","")
        val couleurA=sp.getString("CAR_COLOR","")
        val current = Date()
        val formatter = SimpleDateFormat("MMM/dd/yyyy-HH:mma", Locale.getDefault())
        val date = formatter.format(current)
        finish.setOnClickListener {
            val nameB=id_prenom.text.toString()
            val lastnameB=id_lastname.text.toString()
            val addressB=id_adress.text.toString()
            val marqueB=id_marque.text.toString()
            val assuranceB=id_assurance.text.toString()
            val couleurB=id_color.text.toString()
            val numeropermisB=id_permis.text.toString()
            vehicleB.sendReport(nameA!!,"","tunis",marqueA!!,assuranceA!!,couleurA!!,nameB,lastnameB,addressB,marqueB,assuranceB,couleurB,numeropermisB,date,localisation)

        }

    }
}
