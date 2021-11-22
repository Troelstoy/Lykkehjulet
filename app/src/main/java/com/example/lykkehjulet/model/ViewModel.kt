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
import com.example.lykkehjulet.R
import com.example.lykkehjulet.allWordsList
import com.google.android.material.dialog.MaterialAlertDialogBuilder

//TODO: Får en fejl der siger no adapter attached; skipping layout

class ViewModel : ViewModel() {

    //TODO: Skal vises på fragment skærmen
    //TODO: Skal have den rigtige logik
    private var lives : Int = 5

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private var wordsList: MutableList<String> = mutableListOf()

    private lateinit var currentWord: String

    private val _currentWordView = MutableLiveData<String>()
    val currentWordView : LiveData<String>
        get() = _currentWordView


    private fun getNextWord() {
        currentWord = allWordsList.random()
    }

    fun getlives() : Int{
        return lives
    }

    fun wrongGuess(){
        lives = lives - 1
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

    private fun increaseScore(amount : Int) {
        _score.value = (_score.value)?.plus(amount)
    }


}



