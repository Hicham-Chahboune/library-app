package com.example.ttss.data.api

import com.example.ttss.data.api.services.ApiService
import com.google.gson.Gson
import com.google.gson.*
import okhttp3.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL: String = "https://api.itbook.store/1.0/search/mongodb/"

    private val gson : Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val httpClient : OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService : ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }

}