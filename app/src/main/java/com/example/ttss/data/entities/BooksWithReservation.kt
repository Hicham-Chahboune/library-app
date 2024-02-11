package com.example.ttss.data.entities

import androidx.room.Embedded

class BooksWithReservation (
    @Embedded
    var book: Book?,

    @Embedded
    var user: User?,

    @Embedded
    var reservation: Reservation
)