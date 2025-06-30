package com.talhakasikci.wordapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.talhakasikci.wordapp.data.WordEntry
import com.talhakasikci.wordapp.data.WordRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WordListViewModel(application: Application): AndroidViewModel(application) {
    private val repository = WordRepository(application)

    private val _words = MutableLiveData<List<WordEntry>>()
    val words: LiveData<List<WordEntry>> = _words

    //adapter'a kelimeyi göndermek için
    val favoriteSet: LiveData<Set<String>> = repository
        .favoriteWordsFlow
        .map { list -> list.map { it.word }.toSet() }
        .asLiveData()

    val favoriteWords: LiveData<List<WordEntry>> = repository
        .favoriteWordsFlow                    // Flow<List<WordEntry>>
        .asLiveData()

    init{
        viewModelScope.launch {
            val list = repository.getAllWords()
            _words.value = list
        }
    }

    fun toggleFavorite(entry: WordEntry) {
        viewModelScope.launch {
            val word = entry.word
            if(repository.isFavorite(word)){
                repository.removeFavorite(entry)
            }else{
                repository.addFavorite(entry)
            }
        }
    }

}