package com.example.alertaccident.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.alertaccident.helper.isRegistrationValid
import com.example.alertaccident.presentation.IregisterPresenter
import com.example.alertaccident.presentation.RegisterPresenterImpl
import com.example.alertaccident.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUp : Fragment(),SignupView {
    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext,message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext,message, Toast.LENGTH_SHORT).show()
    }

    internal lateinit var registerpresnter:IregisterPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerpresnter=RegisterPresenterImpl(this)
        btn_submit.setOnClickListener {
            val username=id_name.text.toString()
            val  email=id_email.text.toString()
            val password=id_password.text.toString()
            val phone=id_phone.text.toString()
            val repeat_password=id_confirm_password.text.toString()
            if (isRegistrationValid(email,password,username,phone,repeat_password) == -1) {
                registerpresnter.onRegister(id_email.text.toString(), id_password.text.toString(),id_name.text.toString()
                ,id_phone.text.toString(),id_confirm_password.text.toString())
                findNavController().navigate(R.id.signIn)
            }
            else {
                registerpresnter.onRegister(id_email.text.toString(), id_password.text.toString(),id_name.text.toString()
                    ,id_phone.text.toString(),id_confirm_password.text.toString())
            }
        }
    }
}
