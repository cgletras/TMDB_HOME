package com.example.tmdb.data.repository.local.dao

import androidx.room.*
import com.example.tmdb.data.repository.local.entity.FavoriteMedia

@Dao
interface FavoriteMediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteMedia: FavoriteMedia)

    @Query("DELETE FROM serie_table")
    fun deleteAll()

    @Query("SELECT * FROM serie_table ORDER BY instant DESC")
    fun getAllFavorites():List<FavoriteMedia>

    @Delete
    fun deleteSerie(favoriteMedia: FavoriteMedia)
}