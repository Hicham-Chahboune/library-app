package com.example.ttss.data.api.services

import com.example.ttss.data.api.model.ApiResponse
import com.example.ttss.data.api.model.BookAPI
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.GET

interface ApiService {

    @GET("/")
    suspend fun getBooks(): Response<ApiResponse>

}