package com.example.ttss.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = ["bookId"],
            childColumns = ["rvIdBook"],
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["idUser"],
            childColumns = ["rvIdUser"],
        ),
    ]
)
data class Reservation (
    @PrimaryKey(autoGenerate = true)
    var idReservation:Int?,
    var rvIdBook:Int,
    var rvIdUser:Int,
    var isReserved:Boolean,
    var isTaken:Boolean,
    var isReturned:Boolean,
    var dateReservation:Long?,
    var dateReturn:Long?
){
    constructor(rvIdBook:Int,rvIdUser:Int):this(null,rvIdBook,rvIdUser,false,false,false,null,null)
}