package com.example.lykkehjulet.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lykkehjulet.randomWords

//TODO: Får en fejl der siger no adapter attached; skipping layout

class ViewModel : ViewModel() {

    private val _lives = MutableLiveData(5)
    val lives: LiveData<Int>
        get() = _lives

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score


    private var wordsList: MutableList<String> = mutableListOf()


    init {
        getNextWord()
        println(currentWord)
    }

    private lateinit var currentWord: String

    lateinit var guessWord :String

    private fun getNextWord() {
        currentWord = randomWords.random()
    }

    fun initGuessWord(): String {
        val builder = StringBuilder()
        for (i in currentWord.indices){
            builder.append("-")
        }
        guessWord = builder.toString()

        return guessWord
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
            println("Guessword inden " + guessWord)
            for (i in currentWord.indices){
                if (currentWord[i] == playerLetter){
                    guessWord = guessWord.replaceRange(i,i+1,playerLetter.toString())
                }
            }

            println("Guessword efter " + guessWord)

            return true
        }
        wrongGuess(1)
        return false
    }

    fun increaseScore(amount : Int) {
        _score.value = (_score.value)?.plus(amount)
        println(currentWord)
        println(guessWord)
    }

    fun wrongGuess(amount: Int){
        _lives.value =(_lives.value)?.minus(amount)
    }


}



