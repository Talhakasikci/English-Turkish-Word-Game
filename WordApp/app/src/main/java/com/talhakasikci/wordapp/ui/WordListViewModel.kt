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

    //bottom navigation için
    private val _selectedLevel = MutableLiveData<String>()
    val selectedLevel :LiveData<String> = _selectedLevel

    private val _selectedLetter = MutableLiveData<String>()
    val selectedLetter : LiveData<String> = _selectedLetter

    private val _currentMode = MutableLiveData<String>()
    val currentMode : LiveData<String> = _currentMode

    private val _needLevelSelection = MutableLiveData<Boolean>()
    val needLevelSelection: LiveData<Boolean> = _needLevelSelection

    private val _needLetterSelection = MutableLiveData<Boolean>()
    val needLetterSelection: LiveData<Boolean> = _needLetterSelection





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

        resetNavigationState()
    }

    fun setNavigationForPractise(){
        _currentMode.value = "practise"
        _needLevelSelection.value = true
        _needLetterSelection.value = true
        _selectedLevel.value = ""
        _selectedLetter.value = ""
    }
    fun setNavigationForQuiz(){
        _currentMode.value = "quiz"
        _needLevelSelection.value = true
        _needLetterSelection.value = true
        _selectedLevel.value = ""
        _selectedLetter.value = ""
    }
    fun setnavigationForFavorites(){
        _currentMode.value = "fav"
        _needLevelSelection.value = false
        _needLetterSelection.value = false
        _selectedLevel.value = ""
        _selectedLetter.value = ""
    }
    fun selectedLevel(level:String){
        _selectedLevel.value = level
        _needLevelSelection.value = false
    }
    fun selectedLetter(letter:String){
        _selectedLetter.value = letter
        _needLetterSelection.value = false
    }


    private fun resetNavigationState() {
        _currentMode.value = ""
        _needLevelSelection.value = false
        _needLetterSelection.value = false
        _selectedLevel.value = ""
        _selectedLetter.value = ""
    }
    fun canNavigateToWords():Boolean{
        return when(_currentMode.value){
            "fav"->true
            "practise"-> !_selectedLevel.value.isNullOrEmpty() && !_selectedLetter.value.isNullOrEmpty()
            else->false
        }
    }

    fun canNavigateToQuiz():Boolean{
        return _currentMode.value == "quiz" && !_selectedLevel.value.isNullOrEmpty() && !_selectedLetter.value.isNullOrEmpty()
    }

    fun isFav(word:String){
        viewModelScope.launch {
            if(repository.isFavorite(word)){
            }else{

            }
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