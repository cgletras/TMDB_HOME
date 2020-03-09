package com.example.tmdb.data.repository.local.dao

import androidx.room.*

import com.example.tmdb.data.repository.remote.entity.Serie

@Dao
interface SerieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(serie: Serie)

    @Query("DELETE FROM serie_table")
    fun deleteAll()

    @Query("SELECT * FROM serie_table ORDER BY instant DESC")
    fun getAllSeries():List<Serie>

    @Delete
    fun deleteSerie(serie: Serie)
}
