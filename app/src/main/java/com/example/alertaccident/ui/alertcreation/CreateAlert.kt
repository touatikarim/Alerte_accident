package com.example.alertaccident.ui.alertcreation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.alertaccident.R
import com.example.alertaccident.helper.UiUtils
import kotlinx.android.synthetic.main.fragment_create_alert.*
import java.lang.System.`in`


class CreateAlert : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view, activity!!)

    }
}
