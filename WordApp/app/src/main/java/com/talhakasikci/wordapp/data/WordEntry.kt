package com.talhakasikci.wordapp.data
import kotlinx.serialization.Serializable

@Serializable
data class WordEntry(
    val word: String,
    val meaning: String,
    val level: String
)
