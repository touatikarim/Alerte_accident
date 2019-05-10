package com.example.alertaccident.presentation

import android.content.Context
import android.location.Location
import android.util.DisplayMetrics
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
import android.location.Location.distanceBetween
import com.example.alertaccident.retrofit.UserManager


class AlertsPresenterImpl(internal var alertsView: AlertsView):IAlertsPresenter {

    private lateinit var context:Context
    private lateinit var adapter: AlertAdapter
    private lateinit var alerts:ArrayList<AlertModel>

    override fun showAlerts(recyclerView:RecyclerView) {
        val sp = UserManager.getSharedPref(context)
        val user_latitude=sp.getString("USER_LAT","").toDouble()
        val user_longitude=sp.getString("USER_LNG","").toDouble()
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
                    val alert_lat=p!!.latitude.toDouble()
                    val alert_long= p.longitude.toDouble()
                    val distance=getDistanceBetween(user_latitude,user_longitude,alert_lat,alert_long)
                    if(distance<1000){
                    alerts.add(p)}
                }
                adapter = AlertAdapter(alerts,context)
                adapter.setHasStableIds(true)
                recyclerView.adapter = adapter
                alertsView.load(View.GONE)
                recyclerView.setHasFixedSize(true)
                recyclerView.setItemViewCacheSize(20)


                recyclerView.visibility=View.VISIBLE
            }

        })
    }

    override fun setMainViewContext(context: Context) {
            this.context=context
    }

    fun getDistanceBetween(startlat:Double,startlong:Double,endlat:Double,endlong:Double): Double {
        val result = FloatArray(1)
        Location.distanceBetween(
            startlat, startlong,
            endlat, endlong, result
        )
        return result[0].toDouble()
    }

//    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
//        val theta = lon1 - lon2
//        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + (Math.cos(deg2rad(lat1))
//                * Math.cos(deg2rad(lat2))
//                * Math.cos(deg2rad(theta)))
//        dist = Math.acos(dist)
//        dist = rad2deg(dist)
//        dist = dist * 60.0 * 1.1515
//        return dist
//    }
//
//    private fun deg2rad(deg: Double): Double {
//        return deg * Math.PI / 180.0
//    }
//
//    private fun rad2deg(rad: Double): Double {
//        return rad * 180.0 / Math.PI
//    }

}
