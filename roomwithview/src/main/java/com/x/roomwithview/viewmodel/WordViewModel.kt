package com.x.roomwithview.viewmodel

import androidx.lifecycle.*
import com.x.roomwithview.WordRepository
import com.x.roomwithview.bean.Word
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 * Author : Ray
 * Time : 7/14/21 4:17 PM
 * Description :
 */
class WordViewModel(private val repository: WordRepository) : ViewModel() {

    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }

}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}