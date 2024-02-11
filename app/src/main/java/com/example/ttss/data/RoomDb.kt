package com.example.ttss.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ttss.data.entities.*
import kotlinx.coroutines.*

@Database(entities = [User::class,Notification::class, Categorie::class, Book::class, Reservation::class, Favorite::class], version = 1)
abstract class RoomDb: RoomDatabase() {

    open abstract fun userDao():Dao

    companion object{
        @Volatile
        private var INSTANCE:RoomDb? = null

        fun getDb(context:Context):RoomDb?{
            if(INSTANCE==null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,RoomDb::class.java,"eilcobook")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                    return INSTANCE

                }
            }
            return INSTANCE
        }


        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val dao = INSTANCE?.userDao()
                GlobalScope.launch(Dispatchers.IO){
                    dao?.insertCategorie(Categorie(1,"Math"))
                    dao?.insertCategorie(Categorie(2,"Informatique"))
                    dao?.insertCategorie(Categorie(3,"Mecanique"))
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

            }
        }

    }


}