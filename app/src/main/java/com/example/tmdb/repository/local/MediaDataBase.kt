package com.example.tmdb.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb.repository.local.dao.MovieDao
import com.example.tmdb.repository.local.dao.SerieDao
import com.example.tmdb.repository.data.Movie
import com.example.tmdb.repository.data.Serie

@Database(entities = [Movie::class, Serie::class], version = 1, exportSchema = false)
abstract class MediaDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun serieDao(): SerieDao
}