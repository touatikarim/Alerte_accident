package com.example.alertaccident.Ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.alertaccident.Helper.isDataValid
import com.example.alertaccident.Presentation.IloginPresenter
import com.example.alertaccident.Presentation.LoginPresenterImpl
import com.example.alertaccident.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_sign_in.*


class SignIn : Fragment(),SigninView {
    override fun onSuccess(message: String) {
        Toasty.success(activity!!.baseContext,message,Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(activity!!.baseContext,message,Toast.LENGTH_SHORT).show()}


    internal lateinit var loginpresnter:IloginPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginpresnter=LoginPresenterImpl(this)
        btn_login.setOnClickListener {
            val  email=id_email.text.toString()
            val password=id_password.text.toString()
            if (isDataValid(email,password) == -1) {
                loginpresnter.onLogin(id_email.text.toString(), id_password.text.toString())
                findNavController().navigate(R.id.home2)
            }
            else {
                loginpresnter.onLogin(id_email.text.toString(), id_password.text.toString())
            }
        }
    }
}
