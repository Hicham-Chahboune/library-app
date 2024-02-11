package com.example.ttss.data.api.model

import com.google.gson.annotations.SerializedName
/*
data class BookAPI(
    val bookId: Int,
    val title: String,
    val idCategoriefk: Int,
    val auteur: String,
    val nombrePages: Int,
    val bookImage: String,
    val description: String
)*/

data class BookAPI(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    val image: String,
    val url: String
)
data class ApiResponse(
    val error: String,
    val total: String,
    val page: String,
    val books: List<BookAPI>
)
