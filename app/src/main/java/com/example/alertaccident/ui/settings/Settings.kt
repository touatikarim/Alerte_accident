package com.example.alertaccident.ui.settings


import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.GPSUtils
import kotlinx.android.synthetic.main.fragment_settings.*


class Settings : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val options = navOptions {
//            anim {
//                enter = R.anim.slide_in_right
//                exit = R.anim.slide_out_left
//                popEnter = R.anim.slide_in_left
//                popExit = R.anim.slide_out_right
//            }
//        }
        view.findViewById<TextView>(R.id.id_update)?.setOnClickListener {
            findNavController().navigate(R.id.action_settings_dest_to_update_profile, null, Constants.options)
        }

        view.findViewById<TextView>(R.id.id_change_pass)?.setOnClickListener {
            findNavController().navigate(R.id.action_settings_dest_to_reset_pass,null,Constants.options)
        }
        view.findViewById<TextView>(R.id.id_desac_acc)?.setOnClickListener {
            findNavController().navigate(R.id.action_settings_dest_to_desactivate_Acc,null,Constants.options)
        }
        if(GPSUtils.isLocationEnabled(activity!!.baseContext)){
            gps_switch.isChecked=true
            gps.text=activity!!.baseContext.getString(R.string.disablegps)

        }



    }
}
