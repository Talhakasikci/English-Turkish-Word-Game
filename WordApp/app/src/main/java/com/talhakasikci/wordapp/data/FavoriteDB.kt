package com.talhakasikci.wordapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavoriteWordEntity::class],version = 1)
abstract class FavoriteDB:RoomDatabase() {
    abstract fun favoriteWordDAO(): FavoriteWordDAO

    companion object{
        @Volatile
        private var INSTANCE: FavoriteDB? = null

        fun getInstance(context: Context):FavoriteDB{
            return INSTANCE?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDB::class.java,
                    "favorite_database"
                ).build()
                INSTANCE = instance
                instance

            }
        }
    }
}