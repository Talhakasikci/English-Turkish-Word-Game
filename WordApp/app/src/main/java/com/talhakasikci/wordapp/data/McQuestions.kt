package com.talhakasikci.wordapp.data

data class McQuestions(
    val englishWord: String,
    val correctMeaning: String,
    val options: List<String>,
    val level: String
)
