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
import androidx.preference.PreferenceFragmentCompat

import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.GPSUtils
import kotlinx.android.synthetic.main.fragment_settings.*
import androidx.preference.Preference
import androidx.preference.SwitchPreference
import com.example.alertaccident.model.User
import com.example.alertaccident.retrofit.UserManager
import com.google.android.material.snackbar.Snackbar


class Settings :  PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.app_preferences)
    }


//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_settings, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val Pref_update=findPreference("update")
        val Pref_pass=findPreference("update_pass")
        val Pref_acc=findPreference("dess_acc")
        val Pref_Gps=findPreference("Gps")
        val Pref_notif=findPreference("notif")
        val Pref_car=findPreference("accident")

//        (Pref_notif as SwitchPreference).isChecked=true
//        (Pref_car as SwitchPreference).isChecked=true


        Pref_update.setOnPreferenceClickListener {
           findNavController().navigate(R.id.action_settings_dest_to_update_profile, null, Constants.options)
           true
       }
        Pref_pass.setOnPreferenceClickListener {
            findNavController().navigate(R.id.action_settings_dest_to_reset_pass,null,Constants.options)
            true
        }
        Pref_acc.setOnPreferenceClickListener {
            findNavController().navigate(R.id.action_settings_dest_to_desactivate_Acc,null,Constants.options)
            true
        }


//        view.findViewById<TextView>(R.id.id_update)?.setOnClickListener {
//            findNavController().navigate(R.id.action_settings_dest_to_update_profile, null, Constants.options)
//        }
//
//        view.findViewById<TextView>(R.id.id_change_pass)?.setOnClickListener {
//            findNavController().navigate(R.id.action_settings_dest_to_reset_pass,null,Constants.options)
//        }
//        view.findViewById<TextView>(R.id.id_desac_acc)?.setOnClickListener {
//            findNavController().navigate(R.id.action_settings_dest_to_desactivate_Acc,null,Constants.options)
//        }
        if(GPSUtils.isLocationEnabled(activity!!.baseContext)) {
            (Pref_Gps as SwitchPreference).isChecked=true
        }
//        Pref_car.setOnPreferenceClickListener {
//            if((it as SwitchPreference).isChecked)
//            {
//                UserManager.detectCrash(activity!!.baseContext,true)
//            }
//            else {
//                UserManager.detectCrash(activity!!.baseContext,false)
//            }
//            true
//        }
        Pref_car.setOnPreferenceChangeListener { preference, newValue ->
            if ((preference as SwitchPreference).isChecked){
                //UserManager.detectCrash(activity!!.baseContext,false)
                UserManager.setAccidentDetectionService(false,activity!!.baseContext)
                Snackbar.make(view, "service desactivated", Snackbar.LENGTH_LONG).show()
            }
            else {


               /// UserManager.detectCrash(activity!!.baseContext,true)
                UserManager.setAccidentDetectionService(true,activity!!.baseContext)
                Snackbar.make(view, "service activated", Snackbar.LENGTH_LONG).show()
            }
            true
        }
        Pref_notif.setOnPreferenceChangeListener { preference, newValue ->
            if ((preference as SwitchPreference).isChecked){
                //UserManager.detectCrash(activity!!.baseContext,false)
                UserManager.setNotifService(false,activity!!.baseContext)
                Snackbar.make(view, "Notification desactivated", Snackbar.LENGTH_LONG).show()
            }
            else {

                /// UserManager.detectCrash(activity!!.baseContext,true)
                UserManager.setNotifService(true,activity!!.baseContext)
                Snackbar.make(view, "Notification activated", Snackbar.LENGTH_LONG).show()
            }

            true
        }
    }







    }

