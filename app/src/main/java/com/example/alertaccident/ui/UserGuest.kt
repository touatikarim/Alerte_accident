package com.example.alertaccident.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.alertaccident.R
import kotlinx.android.synthetic.main.activity_user.*


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
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        view.findViewById<Button>(R.id.btn_login)?.setOnClickListener {
            findNavController().navigate(R.id.action_userGuest_to_signIn, null, options)
        }
        view.findViewById<Button>(R.id.btn_sign)?.setOnClickListener {
            findNavController().navigate(R.id.action_userGuest_to_signUp, null, options)
        }
        view.findViewById<Button>(R.id.btn_guest)?.setOnClickListener {
            findNavController().navigate(R.id.action_userGuest_to_guest, null, options)
        }
    }

}
