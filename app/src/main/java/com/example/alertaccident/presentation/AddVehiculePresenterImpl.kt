package com.example.alertaccident.presentation

import android.content.Context
import android.net.Uri
import android.os.Handler

import android.view.View
import com.example.alertaccident.R

import com.example.alertaccident.model.VehiculeModel

import com.example.alertaccident.ui.Vehicule.AddVehicleView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

import java.io.File

class AddVehiculePresenterImpl(internal var addVehicleView: AddVehicleView):IAddVehiculePresenter {


    lateinit  var context: Context
    private var images:String?=null

    override fun addVehicule(marque: String, immatricule: String, assurance: String, couleur: String, nombrePortes: String,id:String) {
//        val vehicule=VehiculeModel(marque,immatricule,assurance,couleur,nombrePortes,images)
//        RetrofitManager.getInstance(Constants.baseurl).service!!.addVehicule(vehicule).enqueue(object :Callback<ApiResponse>{
//
//            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
//                if (response.code() == 200) {
//                    addVehicleView.navigate()
//                    Handler().postDelayed({ addVehicleView.onSuccess(response.body()!!.message) }, 1500)
//                }
//            }
//            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                addVehicleView.onError(t.message!!)
//            }
//
//        }
//        )



        val ref = FirebaseDatabase.getInstance().getReference("Vehicules")
        val vehicule_id = ref.push().key
        val vehicule = VehiculeModel(marque,immatricule,assurance,couleur,nombrePortes,id,images)
        ref.child(vehicule_id!!).setValue(vehicule).addOnCompleteListener {
            addVehicleView.load()
            Handler().postDelayed({addVehicleView.onSuccess(context.getString(R.string.send_alert)) },1500)

            addVehicleView.navigate()
        }



    }
    override fun sendImage(path: String) {
        val storage_ref = FirebaseStorage.getInstance().getReference()
        val file= Uri.fromFile(File(path))
        val image=storage_ref.child("car/${file.lastPathSegment}")
        val uploadtask=image.putFile(file)
        uploadtask.addOnSuccessListener {
            image.getDownloadUrl().addOnSuccessListener {
                    Uri->images=Uri.toString()
                addVehicleView.load_image(View.GONE)
                addVehicleView.onSuccess("Picture stored successfully")


            }
        }
    }

    override fun setMainViewContext(context: Context) {this.context=context }
}