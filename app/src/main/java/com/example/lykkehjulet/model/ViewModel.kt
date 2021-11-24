package com.example.lykkehjulet.model

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.lykkehjulet.allWordsList

//TODO: Får en fejl der siger no adapter attached; skipping layout

class ViewModel : ViewModel() {

    //TODO: Skal vises på fragment skærmen
    //TODO: Skal have den rigtige logik
    private val _lives = MutableLiveData(5)
    val lives: LiveData<Int>
        get() = _lives

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score


    private var wordsList: MutableList<String> = mutableListOf()


    //TODO: Bliver ikke sat til nogen værdi
    private val _currentWordView = MutableLiveData<String>()
    val currentWordView : LiveData<Spannable> = Transformations.map(_currentWordView) {
        if (it == null) {
            SpannableString("")
        } else {
            val currentWordView = it.toString()
            val spannable: Spannable = SpannableString(currentWordView)
            spannable.setSpan(
                TtsSpan.VerbatimBuilder(currentWordView).build(),
                0,
                currentWordView.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannable
        }
    }

    init {
        getNextWord()
        println(currentWord)
    }

    private lateinit var currentWord: String


    private fun getNextWord() {
        currentWord = allWordsList.random()
    }

    fun GuessWord(): String {
        return currentWord
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

    fun increaseScore(amount : Int) {
        _score.value = (_score.value)?.plus(amount)
    }

    fun wrongGuess(amount: Int){
        _lives.value =(_lives.value)?.minus(amount)
    }


}



