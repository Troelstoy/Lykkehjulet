package com.example.lykkehjulet.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lykkehjulet.*
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

//TODO: Får en fejl der siger no adapter attached; skipping layout

class ViewModel : ViewModel() {
    private var _isGameWon = false

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
        if (playerWord.uppercase().equals(currentWord.uppercase())) {
            return true
        }
        else changeUserLife(-1)
        return false
    }

    fun isUserLetterCorrect(playerLetter : Char) :Boolean{
        if(currentWord.uppercase().contains(playerLetter.uppercase())){
            showLetter(playerLetter)

            return true
        }
        changeUserLife(-1)
        return false
    }



    fun changeScore(isPlayerBankrupt : Boolean) {

        if(isPlayerBankrupt == TRUE){
            _score.value = 0
        } else {
            val rand = (1..3).random()
            when (rand) {
                1 -> _score.value = (_score.value)?.plus(500)
                2 -> _score.value = (_score.value)?.plus(700)
                3 -> _score.value = (_score.value)?.plus(1000)
            }
        }


        //TODO: Skal slettes
        println(currentWord)
        //println(guessWord)
        //println(currentCategoryString)

    }

    fun changeUserLife(amount: Int){
        _lives.value =(_lives.value)?.plus(amount)
    }


    fun spinWheel() : String {
        val rand = (0.. spinWheelTypes.values().size-1).random()
        val wheelResult = spinWheelTypes.values()[rand]

        when (wheelResult){
            spinWheelTypes.INCREASE_SCORE -> changeScore(FALSE)
            spinWheelTypes.BANKRUPT       -> changeScore(TRUE)
            spinWheelTypes.INCREASE_LIFE -> changeUserLife(1)
            spinWheelTypes.DECREASE_LIFE  -> changeUserLife(-1)
        }

        println(wheelResult.description)

        return wheelResult.description
    }

    private fun showLetter(playerLetter: Char) {
        for (i in currentWord.indices){
            if (currentWord[i].uppercase() == playerLetter.uppercase()){
                guessWord = guessWord.replaceRange(i,i+1,playerLetter.toString())
            }
        }
    }

    fun wordisGuessed() : Boolean{
        if(guessWord.uppercase() == currentWord.uppercase()){
            return true
        }
        return false
    }

}

enum class spinWheelTypes(val description : String){
    INCREASE_SCORE("Du får flere point!"),
    INCREASE_LIFE ("Du har fået et ekstra liv"),
    BANKRUPT ("Du er gået fallit"),
    DECREASE_LIFE("Du har mistet et liv")
}





