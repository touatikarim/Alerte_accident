package com.example.alertaccident.ui.home

import android.content.Context
import android.content.pm.SigningInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController

import com.example.alertaccident.R
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header.*


class Home : Fragment() {
    val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        create_alert.setOnClickListener {
            findNavController().navigate(R.id.action_home_dest_to_createAlert,null,options)
        }

    }
}
