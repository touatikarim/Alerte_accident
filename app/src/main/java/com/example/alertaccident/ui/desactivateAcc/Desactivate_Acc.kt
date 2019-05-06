package com.example.alertaccident.ui.desactivateAcc

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions


import com.example.alertaccident.R
import com.example.alertaccident.helper.UiUtils
import com.example.alertaccident.presentation.DesactivateAccPresenterImpl
import com.example.alertaccident.presentation.IDesactivateAccPresenter
import com.example.alertaccident.retrofit.UserManager
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_desactivate__acc.*

class Desactivate_Acc : Fragment(),DesactivateAccView {
   internal lateinit var desactivateaccpresenter:IDesactivateAccPresenter
    val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }
    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigate() {
        load()
        Handler().postDelayed({ findNavController().navigate(R.id.action_desactivate_Acc_to_connexion, null, options);activity!!.finish() }, 1500)
    }

    override fun load() {
        val progressBar = desac
        progressBar.setVisibility(View.VISIBLE)
        Handler().postDelayed({ progressBar.setVisibility(View.GONE) }, 1500)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_desactivate__acc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UiUtils.hideKeyboardOntouch(view, activity!!)
        desactivateaccpresenter=DesactivateAccPresenterImpl(this)
        desactivateaccpresenter.setMainViewContext(activity!!.baseContext)
        val sp = UserManager.getSharedPref(activity!!.baseContext)
        val password=sp.getString("USER_PWD","")
        val user_id=sp.getString("USER_ID","")
        btn_desactivate.setOnClickListener {
            val password_entered=id_desactivate_pass.text.toString()
            if(password==password_entered){
                desactivateaccpresenter.onDesactivateAcc(user_id)
            }
            else
                onError(activity!!.baseContext.getString(R.string.wrongpass))


        }
    }
}
