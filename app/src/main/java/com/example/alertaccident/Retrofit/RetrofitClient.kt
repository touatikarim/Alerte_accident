package com.example.alertaccident.Retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private var retrofit : Retrofit?=null
    val instance:Retrofit
   get() {
        if (retrofit == null)
        {
            retrofit = Retrofit.Builder()
                .baseUrl("")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        }
        return retrofit!!
   }
    }
