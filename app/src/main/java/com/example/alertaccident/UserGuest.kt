package com.example.alertaccident

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserGuest.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UserGuest.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
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
    }
}
