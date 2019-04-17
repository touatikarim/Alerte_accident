package com.example.alertaccident.retrofit

import com.example.alertaccident.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface RetrofitServices {

    @POST("auth/Login/")
   fun loginuser(@Body body:LoginModel): Call<ApiResponse>

    @POST("user/addUser")
    fun registeruser(@Body body:RegisterModel):Call<ApiResponse>

    @POST("user/addUser")
    fun registerusergoogle(@Body body:RegisterGoogleModel):Call<ApiResponse>

    @POST("user/addUser")
    fun registeruserfacebook(@Body body:RegisterFbModel):Call<ApiResponse>

    @PUT("user/updateUser/{email}")
    fun updateuser(@Path("email") email:String,@Body body: UpdateModel):Call<ApiResponse>

    @POST("user/forgotPassword/")
    fun forgetpass(@Body body:LoginModel):Call<ApiResponse>

}