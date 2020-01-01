package com.example.alertaccident.presentation

import android.content.Context
import android.os.Handler
import android.util.Log
import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.ReportModel
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.reports.VehicleBView
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VehiculeBPresenterImpl(internal var vehicleBView: VehicleBView):IVehiculeBPresenter {
    lateinit var context: Context


    override fun sendReport(nameA: String, lastnameA: String, addressA: String, marqueA: String, assuranceA: String, couleurA: String,
        nameB: String,
        lastnameB: String,
        addressB: String,
        marqueB: String,
        assuranceB: String,
        couleurB: String,
        numeroPermisB: String,
        date: String,
        localisation: String
    )
    {
        val sp = UserManager.getSharedPref(context)
        val user_id=sp.getString("USER_ID","")
        val repotModel=ReportModel(nameA,lastnameA,addressA,couleurA,marqueA,assuranceA,nameB,lastnameB,addressB,couleurB,marqueB,assuranceB,numeroPermisB,localisation,date,user_id)
        RetrofitManager.getInstance(Constants.baseurl).service!!.addReport(repotModel).enqueue(object :Callback<ApiResponse>{

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if(response.code()==200){
                    vehicleBView.navigate()
                   Handler().postDelayed({ vehicleBView.onSuccess(response.body()!!.message) }, 1500)
                    val ref = FirebaseDatabase.getInstance().getReference("Reports")
                    val report_id = ref.push().key
                    ref.child(report_id!!).setValue(repotModel).addOnCompleteListener {
                        Log.d("message firebase","success")
                    }

                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                vehicleBView.onError(t.message.toString())
            }

        })

    }
    override fun setMainViewContext(context: Context) {
        this.context=context
    }
}