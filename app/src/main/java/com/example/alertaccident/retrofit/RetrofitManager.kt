package com.example.alertaccident.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class  RetrofitManager {
    var retrofit: Retrofit? = null
    var service: RetrofitServices? = null

    companion object {
        private var instance: RetrofitManager? = null


        fun getInstance(baseUrl: String): RetrofitManager {
            if (instance == null ||  !instance!!.retrofit!!.baseUrl().equals (baseUrl)) {
                instance = initRetrofit(baseUrl)
            }
            return instance as RetrofitManager
        }

        private fun initRetrofit(baseUrl: String): RetrofitManager {
            val retrofitManager = RetrofitManager()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).build()
            retrofitManager.retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val retrofitService = retrofitManager.retrofit!!.create(RetrofitServices::class.java)
            retrofitManager.service = retrofitService
            return retrofitManager
        }
    }
    }
