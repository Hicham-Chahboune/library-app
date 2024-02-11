package com.example.ttss.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "notifications",foreignKeys = [
ForeignKey(
entity = User::class,
parentColumns = ["idUser"],
childColumns = ["idUserNotif"],
)])
class Notification
    (
    @PrimaryKey(autoGenerate = true)
    var idNotification:Int?,
    var idUserNotif:Int,
    var message:String
)