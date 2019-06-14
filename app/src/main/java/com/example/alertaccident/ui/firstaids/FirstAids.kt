package com.example.alertaccident.ui.firstaids


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import kotlinx.android.synthetic.main.fragment_first_aids.*


class FirstAids : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_aids, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        details_frag.setOnClickListener {
            findNavController().navigate(R.id.action_aids_dest_to_cardiacArrest,null,Constants.options)
        }
    }
}
