package com.example.tmdb.data.repository.local.dao

import androidx.room.*
import com.example.tmdb.data.repository.local.entity.FavoriteMedia

@Dao
interface FavoriteMediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteMedia: FavoriteMedia)

    @Query("DELETE FROM favorites_table")
    fun deleteAll()

    @Query("SELECT * FROM favorites_table ORDER BY instant DESC")
    fun getAllFavorites():List<FavoriteMedia>

    @Delete
    fun deleteFavorite(favoriteMedia: FavoriteMedia)
}