package com.example.tmdb

import android.app.Application
import androidx.room.Room
import com.example.tmdb.dao.MovieDao
import com.example.tmdb.dao.SerieDao
import com.example.tmdb.dataBase.MediaDataBase

class RoomTMDBApplication : Application() {

    companion object {
        lateinit var serieDao: SerieDao
        lateinit var movieDao: MovieDao
    }

    override fun onCreate() {
        super.onCreate()
        val room = Room.databaseBuilder(
            applicationContext,
            MediaDataBase::class.java,
            "media_database"
        ).allowMainThreadQueries().build()

        movieDao = room.movieDao()
        serieDao = room.serieDao()
    }
}