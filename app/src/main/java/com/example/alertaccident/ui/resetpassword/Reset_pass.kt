package com.example.alertaccident.ui.resetpassword

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

import com.example.alertaccident.R
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.presentation.IResetPassPresenter
import com.example.alertaccident.presentation.ResetPassPresenterImpl
import com.example.alertaccident.retrofit.UserManager
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_reset_pass.*


class Reset_pass : Fragment(),ResetpassView {

    lateinit var resetpresenter:IResetPassPresenter
//    val options = navOptions {
//        anim {
//            enter = R.anim.slide_in_right
//            exit = R.anim.slide_out_left
//            popEnter = R.anim.slide_in_left
//            popExit = R.anim.slide_out_right
//        }
//    }
    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigate() {
        load()
        Handler().postDelayed({ findNavController().navigate(R.id.action_reset_pass_to_home_dest, null, Constants.options) }, 1500)

    }

    override fun load() {
        val progressBar = reset_pass
        progressBar?.setVisibility(View.VISIBLE)
        Handler().postDelayed({ progressBar.setVisibility(View.GONE) }, 1500)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view, activity!!)
        resetpresenter=ResetPassPresenterImpl(this)
        resetpresenter.setMainViewContext( activity!!.baseContext)
        val sp = UserManager.getSharedPref(activity!!.baseContext)
        btn_sub_reset.setOnClickListener {
            val old_pass=id_password.text.toString()
            val new_pass=newpass_id.text.toString()
            val confirm_pass=confirmpass_id.text.toString()
            if(new_pass.equals(confirm_pass)){
                resetpresenter.Update_password(old_pass,new_pass)
            }
            else{
                onError(activity!!.baseContext.getString(R.string.identical_pass))
            }
        }

    }
}
