package com.example.alertaccident.ui.alerts


import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.alertaccident.R
import com.example.alertaccident.presentation.AlertsPresenterImpl
import com.example.alertaccident.presentation.IAlertsPresenter
import kotlinx.android.synthetic.main.fragment_alerts.*


class Alerts : Fragment(),AlertsView {
    override fun load(stat: Int) {
       val progressbar=load_alerts
        progressbar?.visibility=stat
    }

    internal lateinit var alertPresenter: IAlertsPresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alerts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alertPresenter=AlertsPresenterImpl(this)
        alertPresenter.setMainViewContext(activity!!.baseContext)
        load(View.VISIBLE)
        alertPresenter.showAlerts(recyclerView,empty_recycler)



    }
}
