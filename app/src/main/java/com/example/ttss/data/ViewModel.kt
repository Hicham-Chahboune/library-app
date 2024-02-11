package com.example.ttss.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ttss.data.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ViewModel(app:Application):AndroidViewModel(app)
{
    private lateinit var repo:Repo


    init {
        val db = RoomDb.getDb(app)
        repo = Repo(db!!.userDao())

    }
    fun insertCategorie(categorie: Categorie){
        viewModelScope.launch(Dispatchers.IO){
            repo.insertCategorie(categorie)
        }
    }
    fun getCategories() : List<Categorie> = runBlocking {
        repo.getCategories()
    }

    fun getCatoriesEquals(kw:String) : Categorie = runBlocking {  repo.getCatoriesEquals(kw)}
    fun getCatoriesById(id:Int) : Categorie = runBlocking {  repo.getCatoriesById(id) }

    fun insertUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repo.insertUser(user)
        }
    }
    fun insertBook(book: Book){
        viewModelScope.launch(Dispatchers.IO){
            repo.insertBook(book)
        }
    }

    fun addFavorite(favorite: Favorite){
        viewModelScope.launch(Dispatchers.IO){
            repo.addFavorite(favorite)
        }
    }
    fun deletaFavorite(favorite: Favorite){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteFavorite(favorite)
        }
    }
    fun addReservation(reservation: Reservation){
        viewModelScope.launch(Dispatchers.IO){
            repo.addReservation(reservation)
        }
    }
    fun getUsersWithReservation(kw: String): LiveData<List<BooksWithReservation>> =  runBlocking {
        repo.getUsersWithReservation(kw)
    }

    fun deleteReservation(reservation: Reservation){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteReservation(reservation)
        }
    }
    fun getByCin(cin: String) : User? = runBlocking { repo.getByCin(cin) }
    fun allBooksWithFavorites(id: Int,kw:String): LiveData<List<BooksWithFavorite>> = runBlocking {
             repo.allBooksWithFavorites(id,kw)
        }
    fun getReservations(reserved: Boolean, taken: Boolean,kw: String): LiveData<List<BooksWithReservation>> =
       runBlocking {   repo.getReservations(reserved, taken,kw) }

    fun getReservationsAverti(kw: String,dateReservation:Long): LiveData<List<BooksWithReservation>> = runBlocking {
        repo.getReservationsAverti(kw,dateReservation)
    }


    fun getAllFavorites(id: Int): LiveData<List<BooksWithFavorite>> =runBlocking{ repo.getAllFavorites(id) }
    fun getReservation(idUser: Int, idBook: Int): Reservation = runBlocking { repo.getReservation(idUser,idBook) }


    fun insertNotification(notification:Notification){
        viewModelScope.launch(Dispatchers.IO){
            repo.insertNotification(notification)
        }
    }
    fun deleteNotification(notification:Notification){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteNotification(notification)
        }
    }
    fun deleteDataById(idList: List<Int>){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteDataById(idList)
        }
    }
    fun getAllnotificationById(idUser:Int):LiveData<List<Notification>> = runBlocking {
        repo.getAllnotificationById(idUser)
    }
     fun deleteBook(book: Book){
         viewModelScope.launch(Dispatchers.IO){
             repo.deleteBook(book)
         }
     }



//     fun insertUser(user: User){
//        viewModelScope.launch(Dispatchers.IO){
//            repo?.insertUser(user)
//
//        }
//    }
//    fun getAllUsers(): LiveData<List<User>> = getUsers!!
//
//    fun getUser():LiveData<User> = repo!!.getUser()



}