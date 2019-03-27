package com.example.alertaccident.Retrofit

import android.database.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ILogin {
    @GET("SignIn")
    @FormUrlEncoded
    fun loginuser(@Field("email") email:String,
                    @Field("password") password:String):Observable<String>
    @POST
    @FormUrlEncoded
    fun registeruser(@Field("name") name:String,
                     @Field("email") email:String,
                     @Field("phone") phone:String,
                     @Field("password") password:String):Observable<String>

}