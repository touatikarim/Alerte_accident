package com.example.alertaccident.retrofit

import com.example.alertaccident.model.ApiResponse
import com.example.alertaccident.model.User
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ILogin {

    @POST("/auth/Login/")
   fun loginuser(@Body body:User): Call<ApiResponse>
    @POST
    @FormUrlEncoded
    fun registeruser(@Field("name") name:String,
                     @Field("email") email:String,
                     @Field("phone") phone:String,
                     @Field("password") password:String):Observable<String>

}