package com.talhakasikci.wordapp.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class WordRepository(private val context: Context){
    private val favoriteDao = FavoriteDB.getInstance(context).favoriteWordDAO()
    private val json = Json {ignoreUnknownKeys = true}

    suspend fun getAllWords(): List<WordEntry> = withContext(Dispatchers.IO) {
        val inputStream = context.assets.open("oxford_3000_words_translated.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        json.decodeFromString<List<WordEntry>>(jsonString)
    }

    val favoriteWordsFlow: Flow<List<WordEntry>> =
        favoriteDao.getAllFavorites()
        .map { list ->
            list.map {
                WordEntry(it.word,it.meaning,it.level)
            } }

    suspend fun addFavorite(word:WordEntry){
        favoriteDao.addFavorite(FavoriteWordEntity(word = word.word, meaning = word.meaning, level = word.level))
    }
    suspend fun removeFavorite(word: WordEntry) {
        favoriteDao.removeFavorite(FavoriteWordEntity(word = word.word, meaning = word.meaning, level = word.level))
    }
    suspend fun isFavorite(word: String): Boolean {
        return favoriteDao.isFavorite(word)
    }
}
