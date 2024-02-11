package com.example.ttss.data.entities

import androidx.room.Entity


@Entity(tableName = "favorites", primaryKeys = ["fvIdBook", "fvIdUser"])
data class Favorite (
    var fvIdBook:Int,
    var fvIdUser:Int
)