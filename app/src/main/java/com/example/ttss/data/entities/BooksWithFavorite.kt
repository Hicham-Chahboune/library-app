package com.example.ttss.data.entities

import androidx.room.Embedded

class BooksWithFavorite (
    @Embedded
    var book: Book,

    @Embedded
    var user: User?,

    @Embedded
    var favorite: Favorite?,

    @Embedded
    var categorie: Categorie?
    )