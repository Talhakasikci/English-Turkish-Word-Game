package com.talhakasikci.wordapp.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class WordRepository(private val context: Context){

    private val json = Json {ignoreUnknownKeys = true}

    suspend fun getAllWords(): List<WordEntry> = withContext(Dispatchers.IO) {
        val inputStream = context.assets.open("oxford_3000_words_translated.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        json.decodeFromString<List<WordEntry>>(jsonString)
    }
}
