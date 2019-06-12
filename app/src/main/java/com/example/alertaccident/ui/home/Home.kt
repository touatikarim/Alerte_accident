package com.example.alertaccident.ui.home

import android.annotation.SuppressLint
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.alertaccident.presentation.HomePresenterImpl
import com.example.alertaccident.presentation.IHomePresenter
import kotlinx.android.synthetic.main.fragment_home.*
import com.example.alertaccident.R
import android.widget.ImageButton
import android.view.MotionEvent
import android.graphics.PorterDuff
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
//      createalert.setOnTouchListener (object:View.OnTouchListener{
//
//          override fun onTouch(view: View?, ev: MotionEvent?): Boolean {
//              if (ev!!.action == MotionEvent.ACTION_DOWN){
//                  val v = view as ImageButton
//                   view.background.setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP)
//                   v.invalidate()
//              }
//              else if (ev.action == MotionEvent.ACTION_UP){
//                  homepresenter.getLocation(activity!!, lat, place, lng)
//                  findNavController().navigate(R.id.action_home_dest_to_createAlert, null, options)
//              }
//              else if (ev.action == MotionEvent.ACTION_BUTTON_RELEASE){
//                  val v = view as ImageButton
//                  view.background.clearColorFilter()
//                  v.invalidate()
//              }
//
//
//              return true
//
//          }
//
//      })


    }
}

