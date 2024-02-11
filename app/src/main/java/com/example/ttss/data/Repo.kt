package com.example.ttss.data

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.ttss.data.entities.*

class Repo (private val dao:Dao)
{
    suspend fun insertUser(user: User)= dao.insertUser(user)
    suspend fun getByCin(cin: String) : User?=dao.getByCin(cin)
    suspend fun insertBook(book: Book) = dao.insertBook(book)
    suspend fun getCategories() : List<Categorie> = dao.getCategories()
    suspend fun getCatoriesEquals(kw:String) : Categorie = dao.getCatoriesEquals(kw)
    suspend fun getCatoriesById(id:Int) : Categorie = dao.getCatoriesById(id)

    suspend fun insertNotification(notification:Notification) = dao.insertNotification(notification)
    suspend fun deleteNotification(notification:Notification) =dao.deleteNotification(notification)
    suspend fun deleteDataById(idList: List<Int>) =dao.deleteDataById(idList)
    fun getAllnotificationById(idUser:Int):LiveData<List<Notification>> = dao.getAllnotificationById(idUser)
    suspend fun deleteBook(book: Book) = dao.deleteBook(book)
    suspend fun addFavorite(favorite: Favorite) =dao.addFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) =dao.deleteFavorite(favorite)
    suspend fun addReservation(reservation: Reservation) = dao.addReservation(reservation)
    suspend fun deleteReservation(reservation: Reservation) = dao.deleteReservation(reservation)
    fun allBooksWithFavorites(id: Int,kw:String): LiveData<List<BooksWithFavorite>> = dao.allBooksWithFavorites(id,kw)
    fun getReservations(reserved: Boolean, taken: Boolean,kw: String): LiveData<List<BooksWithReservation>> = dao.getReservations(reserved, taken,kw)
    fun getUsersWithReservation(kw: String): LiveData<List<BooksWithReservation>> = dao.getUsersWithReservation(kw)
    fun getAllFavorites(id: Int): LiveData<List<BooksWithFavorite>> = dao.getAllFavorites(id)
    suspend fun insertCategorie(categorie: Categorie)=dao.insertCategorie(categorie)
    suspend fun getReservation(idUser: Int, idBook: Int): Reservation = dao.getReservation(idUser,idBook)
    fun getReservationsAverti(kw: String,dateReservation:Long): LiveData<List<BooksWithReservation>> =  dao.getReservationsAverti(kw,dateReservation)
}