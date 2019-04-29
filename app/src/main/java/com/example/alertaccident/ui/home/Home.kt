package com.example.alertaccident.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

import com.example.alertaccident.R
import com.example.alertaccident.presentation.HomePresenterImpl
import com.example.alertaccident.presentation.IHomePresenter
import com.example.alertaccident.retrofit.UserManager
import kotlinx.android.synthetic.main.fragment_home.*


class Home : Fragment() {
    val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }
    lateinit var homepresenter:IHomePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homepresenter=HomePresenterImpl()
        homepresenter.setMainViewContext(activity!!.baseContext)
        val sp = UserManager.getSharedPref(activity!!.baseContext)
        val latitude=sp.getString("USER_LAT","")
        val longitude=sp.getString("USER_LNG","")
        val country=sp.getString("USER_COUNTRY","")
        val city=sp.getString("USER_CITY","")
        val area=sp.getString("USER_AREA","")

        place.text= "$country,$city,\n $area"
        lat.text=latitude
        lng.text=longitude

        create_alert.setOnClickListener {
            homepresenter.getLocation(activity!!)
            findNavController().navigate(R.id.action_home_dest_to_createAlert,null,options)
        }

    }
}
