package com.example.tmdb.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb.dao.MovieDao
import com.example.tmdb.data.Movie
import com.example.tmdb.data.Serie

@Database(entities = [Movie::class, Serie::class], version = 1, exportSchema = false)
abstract class MediaDataBase: RoomDatabase() {
    abstract fun movieDao():MovieDao
}