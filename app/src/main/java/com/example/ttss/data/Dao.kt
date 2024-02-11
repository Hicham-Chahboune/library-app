package com.example.ttss.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.ttss.data.entities.*


@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("select * from users where cin like :cin")
    suspend fun getByCin(cin: String) : User?

    @Query("select * from categories")
    suspend fun getCategories() : List<Categorie>

    @Query("select * from categories where categorieDesignation like :kw")
    suspend fun getCatoriesEquals(kw:String) : Categorie

    @Query("select * from categories where idCategorie=:id")
    suspend fun getCatoriesById(id:Int) : Categorie

    @Insert
    suspend fun insertCategorie(categorie: Categorie)
    @Insert(onConflict = OnConflictStrategy.REPLACE)

    suspend fun insertBook(book: Book)
    @Delete
    suspend fun deleteBook(book: Book)
    @Insert
    suspend fun addFavorite(favorite: Favorite)
    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReservation(reservation: Reservation)
    @Delete
    suspend fun deleteReservation(reservation: Reservation)
    //notifications
    @Insert
    suspend fun insertNotification(notification:Notification)
    @Delete
    suspend fun deleteNotification(notification:Notification)
    @Query("delete from notifications where idNotification in (:idList)")
    suspend fun deleteDataById(idList: List<Int>)
    @Query("select * from notifications where idUserNotif=:idUser")
    fun getAllnotificationById(idUser:Int):LiveData<List<Notification>>


    @Transaction
    @Query("SELECT * FROM (select * from books left JOIN  (select * from favorites inner join users on favorites.fvIdUser = users.idUser where idUser=:id) as ss on books.bookId = ss.fvIdBook where books.title like :kw or books.auteur like :kw) as m inner join categories where m.idCategoriefk=categories.idCategorie")
    fun allBooksWithFavorites(id: Int,kw:String): LiveData<List<BooksWithFavorite>>

    @Transaction
    @Query("SELECT * FROM books inner JOIN  (select * from favorites inner join users on favorites.fvIdUser = users.idUser where idUser=:id) as ss on books.bookId = ss.fvIdBook")
    fun getAllFavorites(id: Int): LiveData<List<BooksWithFavorite>>

    @Query("select * from reservation where rvIdBook=:idBook and rvIdUser=:idUser")
    suspend fun getReservation(idUser: Int, idBook: Int): Reservation

    @Transaction
    @Query("select * from books inner join (select * from users inner join reservation on rvIdUser=users.idUser) as ss on books.bookId=ss.rvIdBook  where ss.isReserved=:reserved and ss.isTaken=:taken and (ss.nom like :kw or ss.prenom like :kw) and isReturned=0")
    fun getReservations(reserved: Boolean, taken: Boolean,kw: String): LiveData<List<BooksWithReservation>>

    @Transaction
    @Query("select * from books inner join (select * from users inner join reservation on rvIdUser=users.idUser) as ss on books.bookId=ss.rvIdBook  where ss.isTaken=1  and (ss.nom like :kw or ss.prenom like :kw) and isReturned=0")
    fun getUsersWithReservation(kw: String): LiveData<List<BooksWithReservation>>

    @Transaction
    @Query("select * from books inner join (select * from users inner join reservation on rvIdUser=users.idUser) as ss on books.bookId=ss.rvIdBook  where ss.isTaken=1 and (ss.nom like :kw or ss.prenom like :kw) and isReturned=0 and dateReservation- :dateReservation < 0")
    fun getReservationsAverti(kw: String,dateReservation:Long): LiveData<List<BooksWithReservation>>




}