package com.example.ttss.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var idUser:Int?,
    var nom:String,
    var prenom:String,
    var Role:String,
    var cin:String,
    var image: ByteArray?,
    var password:String

)