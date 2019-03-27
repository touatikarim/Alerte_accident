package com.example.alertaccident.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.alertaccident.R



class UserGuest : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_guest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.btn_login).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_userGuest_to_signIn)
        )
        view.findViewById<View>(R.id.btn_sign).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_userGuest_to_signUp)
        )
        view.findViewById<View>(R.id.btn_guest).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_userGuest_to_home2)
        )
    }
}
