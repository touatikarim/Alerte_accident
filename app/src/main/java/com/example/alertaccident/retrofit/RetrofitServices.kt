package com.example.alertaccident.retrofit

import com.example.alertaccident.model.*
import retrofit2.Call
import retrofit2.http.*


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

    @DELETE("user/deleteUser/{user_id}")
    fun desactivateaccount(@Path("user_id") user_id:String):Call<ApiResponse>

   @PUT("user/updatePassword/{email}")
   fun updatepassword(@Path("email") email:String, @Body body:PasswordModel):Call<ApiResponse>


}
