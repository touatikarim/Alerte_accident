package com.example.alertaccident.ui.home

import android.annotation.SuppressLint
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.alertaccident.presentation.HomePresenterImpl
import com.example.alertaccident.presentation.IHomePresenter
import kotlinx.android.synthetic.main.fragment_home.*
import com.example.alertaccident.R

import com.example.alertaccident.helper.Constants


class Home : Fragment() {

    lateinit var homepresenter: IHomePresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homepresenter = HomePresenterImpl()
        homepresenter.setMainViewContext(activity!!.baseContext)
        homepresenter.getLocation(activity!!, lat, place, lng)

        createalert.setOnClickListener {
            homepresenter.getLocation(activity!!, lat, place, lng)
            findNavController().navigate(R.id.action_home_dest_to_createAlert, null, Constants.options)
        }



    }
}

