package com.example.tmdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tmdb.data.Movie

@Dao
interface MovieDao {

    @Insert
    fun insert(movie:Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Query("SELECT * FROM movie_table ORDER BY idTable ASC")
    fun getAllWorlds():List<Movie>

    @Delete
    fun deleteWord(movie:Movie)
}
