package com.example.alertaccident.presentation

import android.content.Context
import android.os.Handler
import android.provider.SyncStateContract
import com.example.alertaccident.helper.Constants
import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.retrofit.RetrofitManager
import com.example.alertaccident.ui.desactivateAcc.DesactivateAccView
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response


class DesactivateAccPresenterImpl(internal var desactivateaccview:DesactivateAccView):IDesactivateAccPresenter {
    lateinit var context: Context


    override fun onDesactivateAcc(user_id: String) {
        RetrofitManager.getInstance(Constants.baseurl).service!!.desactivateaccount(user_id)
            .enqueue(object :Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                   if(response.isSuccessful) {
                       desactivateaccview.navigate()
                       Handler().postDelayed({desactivateaccview.onSuccess(response.body()!!.message)},1500)

                   }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                   desactivateaccview.onError(t.message!!)
                }

            })
    }

    override fun setMainViewContext(context: Context) {
        this.context=context
    }
}