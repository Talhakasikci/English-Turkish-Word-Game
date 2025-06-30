package com.talhakasikci.wordapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteWordEntity(
    @PrimaryKey val word: String,
    val meaning: String,
    val level: String
)
