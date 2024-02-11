package com.example.ttss.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "categories")
data class Categorie (
    @PrimaryKey(autoGenerate = true)
    var idCategorie:Int?,
    var categorieDesignation:String

)