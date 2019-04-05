package com.example.alertaccident.retrofit

import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.RegisterModel
import com.example.alertaccident.model.LoginModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface RetrofitServices {

    @POST("auth/Login/")
   fun loginuser(@Body body:LoginModel): Call<ApiResponse>

    @POST("user/Post/")
    fun registeruser(@Body body:RegisterModel):Call<ApiResponse>

}