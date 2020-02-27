package com.example.tmdb.repository.local.dao

import androidx.room.*
import com.example.tmdb.repository.data.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie:Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Query("SELECT * FROM movie_table ORDER BY instant DESC")
    fun getAllMovies():List<Movie>

    @Delete
    fun deleteMovie(movie:Movie)
}
