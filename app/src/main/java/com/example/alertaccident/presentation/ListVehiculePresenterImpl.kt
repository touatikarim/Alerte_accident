package com.example.alertaccident.presentation

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alertaccident.adapters.VehicleAdapter
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.VehiculeModel
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.retrofit.UserManager
import com.example.alertaccident.ui.Vehicule.ListvehicleView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListVehiculePresenterImpl(internal var listvehicleView: ListvehicleView):IListeVehiculePresenter {


    lateinit var context: Context
    private lateinit var adapter: VehicleAdapter
    private lateinit var vehicles:ArrayList<VehiculeModel>
    var items:Int=0


    override fun getListVehicule(recyclerView: RecyclerView, emptyRecyclerView: LinearLayout) {
        val sp = UserManager.getSharedPref(context)
        val user_id=sp.getString("USER_ID","")
        val layoutmanager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutmanager
        val reference= FirebaseDatabase.getInstance().getReference().child("Vehicules")
        reference.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("error",p0.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
               vehicles= ArrayList<VehiculeModel>()
                for (dataSnapshot1 in dataSnapshot.children){
                    val p = dataSnapshot1.getValue(VehiculeModel::class.java)
                    val vehicule_user_id=p!!.id
                    if(user_id==vehicule_user_id){
                        vehicles.add(p)
                    }
                }
                adapter = VehicleAdapter(vehicles,context)
                adapter.setHasStableIds(true)
                recyclerView.adapter = adapter
                items=adapter.itemCount
                listvehicleView.load(View.GONE)
                recyclerView.setHasFixedSize(true)
                recyclerView.setItemViewCacheSize(20)
                if (vehicles.isEmpty()){
                    emptyRecyclerView.visibility=View.VISIBLE

                }
                else{

                    emptyRecyclerView.visibility=View.GONE
                    recyclerView.visibility=View.VISIBLE
                }
            }


        })
    }


    override fun setMainViewContext(context: Context) {
        this.context=context
    }



}