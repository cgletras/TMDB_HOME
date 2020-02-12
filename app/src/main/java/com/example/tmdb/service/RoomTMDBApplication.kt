package com.example.tmdb.service

import android.app.Application
import androidx.room.Room
import com.example.tmdb.dao.MovieDao
import com.example.tmdb.dataBase.MediaDataBase

class RoomTMDBApplication : Application() {

    companion object {
        lateinit var dataBase: MovieDao
    }

    override fun onCreate() {
        super.onCreate()
        val room = Room.databaseBuilder(
            applicationContext,
            MediaDataBase::class.java,
            "media_database"
        ).allowMainThreadQueries().build()

        dataBase = room.movieDao()
    }
}