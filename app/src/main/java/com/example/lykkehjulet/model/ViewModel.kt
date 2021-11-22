package com.example.lykkehjulet.model

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.lykkehjulet.MAX_NO_OF_WORDS
import com.example.lykkehjulet.allWordsList

//TODO: Får en fejl der siger no adapter attached; skipping layout

class ViewModel : ViewModel() {

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private var wordsList: MutableList<String> = mutableListOf()

    private lateinit var currentWord: String

    private val _currentWordView = MutableLiveData<String>()
    val currentWordView : LiveData<Spannable> = Transformations.map(_currentWordView){
        if (it == null) {
            SpannableString("")
        } else {
            val scrambledWord = it.toString()
            val spannable: Spannable = SpannableString(scrambledWord)
            spannable.setSpan(
                TtsSpan.VerbatimBuilder(scrambledWord).build(),
                0,
                scrambledWord.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannable
        }
    }


    private fun getNextWord() {
        currentWord = allWordsList.random()
    }


    init {
        getNextWord()
        println(currentWord)
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

    fun isUserLetterCorrect(playerLetter : Char) :Boolean{
        if(currentWord.contains(playerLetter)){
            return true
        }
        return false
    }

    //TODO: Skal slettes
    fun printword() {
        println(currentWord)

    }

}



