package com.example.lykkehjulet.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lykkehjulet.Danmarkshistorien
import com.example.lykkehjulet.StederKBH
import com.example.lykkehjulet.categories
import com.example.lykkehjulet.randomWords

//TODO: Får en fejl der siger no adapter attached; skipping layout
//TODO: Alle ord skal være i caps

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
    }

    private lateinit var currentWord: String
    private lateinit var currentCategoryList : List<String>
    lateinit var currentCategoryString : String

    lateinit var guessWord :String

    private fun getCategory(){
        val category = categories.random()
        when (category){
            "Steder i Kbh" -> currentCategoryList = StederKBH
            "Danmarkshistorien" -> currentCategoryList = Danmarkshistorien
            "Random" -> currentCategoryList = randomWords
        }
        currentCategoryString = category
    }

    private fun getNextWord() {
        getCategory()
        currentWord = currentCategoryList.random()
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
            return true
        }
        return false
    }

    fun isUserLetterCorrect(playerLetter : Char) :Boolean{
        if(currentWord.contains(playerLetter)){
            for (i in currentWord.indices){
                if (currentWord[i] == playerLetter){
                    guessWord = guessWord.replaceRange(i,i+1,playerLetter.toString())
                }
            }
            return true
        }
        wrongGuess(1)
        return false
    }

    fun increaseScore() {

        val rand = (0..3).random()
        when (rand){
            1 -> _score.value = (_score.value)?.plus(500)
            2 -> _score.value = (_score.value)?.plus(700)
            3 -> _score.value = (_score.value)?.plus(1000)
        }


        //TODO: Skal slettes
        println(currentWord)
        println(guessWord)
        println(currentCategoryString)

    }

    fun wrongGuess(amount: Int){
        _lives.value =(_lives.value)?.minus(amount)
    }


}



