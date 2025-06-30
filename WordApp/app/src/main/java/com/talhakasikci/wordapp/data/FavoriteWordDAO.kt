package com.talhakasikci.wordapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteWordDAO {
    @Insert
    suspend fun addFavorite(fav: FavoriteWordEntity)

    @Delete
    suspend fun removeFavorite(fav: FavoriteWordEntity)

    @Query("select * from favorites")
    fun getAllFavorites(): Flow<List<FavoriteWordEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE word = :w)")
    suspend fun isFavorite(w: String): Boolean

    @Query("select *from favorites where level =:level")
    suspend fun getFavoritesByLevel(level: String): List<FavoriteWordEntity>
}