package com.example.alertaccident.ui.reports



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
import com.example.alertaccident.adapters.ReportAdapter
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.model.ReportModel
import com.example.alertaccident.retrofit.UserManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_accident__reports.*


class Accident_Reports : Fragment() {

    private lateinit var adapter: ReportAdapter
    private lateinit var reports:ArrayList<ReportModel>
    var items:Int=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accident__reports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab_add_report.setOnClickListener {
            findNavController().navigate(R.id.action_accident_Reports_to_vehicleA,null,Constants.options)
        }
        load_reports.visibility= View.VISIBLE
        getReports(recyclerView_reports,empty_recycler_reports)
    }
    fun getReports(recyclerView: RecyclerView,emptyRecyclerView: LinearLayout){
        val sp = UserManager.getSharedPref(activity!!.baseContext)
        val user_id=sp.getString("USER_ID","")
        val layoutmanager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutmanager
        val reference= FirebaseDatabase.getInstance().getReference().child("Reports")
        reference.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("error",p0.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                reports= ArrayList<ReportModel>()
                for (dataSnapshot1 in dataSnapshot.children){
                    val p = dataSnapshot1.getValue(ReportModel::class.java)
                    val report_user_id=p!!.user_id
                    if(user_id==report_user_id){
                        reports.add(p)
                    }
                }
                adapter = ReportAdapter(reports,activity!!.baseContext)
                adapter.setHasStableIds(true)
                recyclerView.adapter = adapter
                items=adapter.itemCount
                load_reports.visibility=View.GONE
                recyclerView.setHasFixedSize(true)
                recyclerView.setItemViewCacheSize(20)
                if (reports.isEmpty()){
                    emptyRecyclerView.visibility=View.VISIBLE

                }
                else{

                    emptyRecyclerView.visibility=View.GONE
                    recyclerView.visibility=View.VISIBLE
                }
            }


        })
    }


}
