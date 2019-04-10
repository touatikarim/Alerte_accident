package com.example.alertaccident.retrofit

import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.RegisterModel
import com.example.alertaccident.model.LoginModel
import com.example.alertaccident.model.UpdateModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface RetrofitServices {

    @POST("auth/Login/")
   fun loginuser(@Body body:LoginModel): Call<ApiResponse>

    @POST("user/Post/")
    fun registeruser(@Body body:RegisterModel):Call<ApiResponse>

    @PUT("user/updateUser/{email}")
    fun updateuser(@Path("email") email:String,@Body body: UpdateModel):Call<ApiResponse>

}