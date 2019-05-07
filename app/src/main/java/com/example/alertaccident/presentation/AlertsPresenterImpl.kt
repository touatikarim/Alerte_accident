package com.example.alertaccident.presentation

import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alertaccident.adapters.AlertAdapter
import com.example.alertaccident.model.AlertModel
import com.example.alertaccident.ui.alerts.AlertsView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_alerts.*

class AlertsPresenterImpl(internal var alertsView: AlertsView):IAlertsPresenter {

    private lateinit var context:Context
    private lateinit var adapter: AlertAdapter
    private lateinit var alerts:ArrayList<AlertModel>
    override fun showAlerts(recyclerView:RecyclerView) {
        val layoutmanager = LinearLayoutManager(context)
        recyclerView.setLayoutManager(layoutmanager)
        val reference= FirebaseDatabase.getInstance().getReference().child("Alerts")
        reference.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("error",p0.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                alerts=ArrayList<AlertModel>()
                for (dataSnapshot1 in dataSnapshot.children) {
                    val p = dataSnapshot1.getValue(AlertModel::class.java)
                    alerts.add(p!!)
                }
                adapter = AlertAdapter(alerts,context)
                recyclerView.adapter = adapter
                alertsView.load(View.GONE)
                recyclerView.visibility=View.VISIBLE
            }

        })
    }
    override fun setMainViewContext(context: Context) {
            this.context=context
    }

}
