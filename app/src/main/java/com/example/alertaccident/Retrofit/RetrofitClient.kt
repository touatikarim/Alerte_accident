package com.example.alertaccident.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private var instance : Retrofit?=null
  fun getInstance():Retrofit {
        if (instance == null)
        {
            instance = Retrofit.Builder()
                .baseUrl("http://internship.mobelite.fr:3000/auth/Login/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
   }
    }
