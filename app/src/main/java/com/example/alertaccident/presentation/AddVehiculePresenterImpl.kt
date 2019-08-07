package com.example.alertaccident.presentation

import android.content.Context
import android.os.Handler
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.VehiculeModel
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.retrofit.RetrofitServices
import com.example.alertaccident.ui.Vehicule.AddVehicleView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddVehiculePresenterImpl(internal var addVehicleView: AddVehicleView):IAddVehiculePresenter {
    lateinit  var context: Context

    override fun addVehicule(marque: String, immatricule: String, assurance: String, couleur: String, nombrePortes: String) {
        val vehicule=VehiculeModel(marque,immatricule,assurance,couleur,nombrePortes)
        RetrofitManager.getInstance(Constants.baseurl).service!!.addVehicule(vehicule).enqueue(object :Callback<ApiResponse>{

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.code() == 200) {
                    addVehicleView.navigate()
                    Handler().postDelayed({ addVehicleView.onSuccess(response.body()!!.message) }, 1500)
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                addVehicleView.onError(t.message!!)
            }

        }
        )


    }

    override fun setMainViewContext(context: Context) {this.context=context }
}