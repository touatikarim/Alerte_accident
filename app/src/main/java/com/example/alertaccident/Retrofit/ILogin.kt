package com.example.alertaccident.Retrofit

import android.database.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.*

interface ILogin {
    @GET("SignIn")
    @FormUrlEncoded
    fun loginuser(@Field("email") email:String,
                    @Field("password") password:String):Observable<String>
}