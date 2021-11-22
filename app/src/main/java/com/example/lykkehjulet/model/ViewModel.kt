package com.example.lykkehjulet.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lykkehjulet.MAX_NO_OF_WORDS
import com.example.lykkehjulet.allWordsList

class ViewModel : ViewModel() {

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    private fun getNextWord() {
        currentWord = allWordsList.random()
    }

    val tempWord = currentWord.toCharArray()


    init {
        Log.d("Fragment", "ViewModel created!")
        getNextWord()
    }

    fun reinitializeData() {
        _score.value = 0
        wordsList.clear()
        getNextWord()
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            //increaseScore()
            return true
        }
        return false
    }


}