package com.talhakasikci.wordapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.talhakasikci.wordapp.data.WordEntry
import com.talhakasikci.wordapp.data.WordRepository
import kotlinx.coroutines.launch

class WordListViewModel(application: Application): AndroidViewModel(application) {
    private val repository = WordRepository(application)

    private val _words = MutableLiveData<List<WordEntry>>()
    val words: LiveData<List<WordEntry>> = _words

    init{
        viewModelScope.launch {
            val list = repository.getAllWords()
            _words.value = list
        }
    }
}