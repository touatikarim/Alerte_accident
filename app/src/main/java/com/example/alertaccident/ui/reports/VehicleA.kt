package com.example.alertaccident.ui.reports

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.alertaccident.R
import com.example.alertaccident.adapters.VehicleAccidentAdapter
import com.example.alertaccident.adapters.VehicleAdapter
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.model.VehiculeModel
import com.example.alertaccident.retrofit.UserManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_vehicle.*


class VehicleA : Fragment() {

lateinit var vehicles:ArrayList<VehiculeModel>
    private lateinit var adapter:VehicleAccidentAdapter
    var items:Int=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sp = UserManager.getSharedPref(activity!!.baseContext)
        val name = sp.getString("USER_NAME", "")
        user_name.text = name
        getListVehicule(recyclerView_car_accident)
        next_page.setOnClickListener {
            findNavController().navigate(R.id.action_vehicleA_to_vehicleB,null,Constants.options)
        }

    }
    fun getListVehicule(recyclerView: RecyclerView) {
        val sp = UserManager.getSharedPref(activity!!.baseContext)
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
                adapter = VehicleAccidentAdapter(vehicles,activity!!.baseContext)
                adapter.setHasStableIds(true)
                recyclerView.adapter = adapter
                items=adapter.itemCount
                //listvehicleView.load(View.GONE)
                recyclerView.setHasFixedSize(true)
                recyclerView.setItemViewCacheSize(20)
                if (vehicles.isEmpty()){
                    empty_recycler.visibility=View.VISIBLE

                }
                else{
                    empty_recycler.visibility=View.GONE
                    recyclerView_car_accident.visibility=View.VISIBLE
                }
            }


        })
    }
}
