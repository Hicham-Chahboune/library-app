package com.example.ttss.data.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE


@Entity(
    tableName = "books",
    foreignKeys = [
        ForeignKey(
            entity = Categorie::class,
            parentColumns = ["idCategorie"],
            childColumns = ["idCategoriefk"],
        )]
)
data class Book(
    @PrimaryKey(autoGenerate = true)
    var bookId: Int?,
    var title:String,
    var idCategoriefk: Int?,
    var auteur: String,
    var nombrePages: Int,
    var bookImage: ByteArray,
    var description:String
)