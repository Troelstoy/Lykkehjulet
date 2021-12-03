package com.example.s152780_lykkehjulet.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.s152780_lykkehjulet.*
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

//TODO: Får en fejl der siger no adapter attached; skipping layout

class ViewModel : ViewModel() {
    private var _isGameWon = false


    //Players lives
    private val _lives = MutableLiveData(5)
    val lives: LiveData<Int>
        get() = _lives

    //Players score
    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    //List of words
    private var wordsList: MutableList<String> = mutableListOf()


    init {
        getNextWord()
    }

    private lateinit var currentWord: String
    private lateinit var currentCategoryList : List<String>
    lateinit var currentCategoryString : String

    lateinit var guessWord :String


    /**
     * Switch statement for different categories
     */

    private fun getCategory(){
        val category = categories.random()
        when (category){
            "Steder i Kbh" -> currentCategoryList = StederKBH
            "Danmarkshistorien" -> currentCategoryList = Danmarkshistorien
            "Random" -> currentCategoryList = randomWords
        }
        currentCategoryString = category
    }

    /**
     * Get next word from a list and show ---- on the screen
     */

    private fun getNextWord() {
        getCategory()
        currentWord = currentCategoryList.random()

        val builder = StringBuilder()
        for (i in currentWord.indices){
            builder.append("-")
        }
        guessWord = builder.toString()
    }

    /**
     * Restart game
     */
    fun reinitializeData() {
        _score.value = 0
        wordsList.clear()
        _isGameWon = FALSE
        setUserLife(5)

        getNextWord()

        println("")
    }

    /**
     * Checks wehter the user word is correct
     */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.uppercase().equals(currentWord.uppercase())) {
            return true
        }
        else changeUserLife(-1)
        return false
    }

    /**
     * Checks wether the user letter is correct
     */
    fun isUserLetterCorrect(playerLetter : Char) :Boolean{
        if(currentWord.uppercase().contains(playerLetter.uppercase())){
            showLetter(playerLetter)

            if(guessWord.uppercase().equals(currentWord.uppercase())){
                _isGameWon = TRUE
            }
            return true
        }
        changeUserLife(-1)
        return false
    }


    /**
     * Changes score by a random value
     */
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
    }


    fun changeUserLife(amount: Int){
        _lives.value =(_lives.value)?.plus(amount)
    }

    fun setUserLife(amount: Int){
        _lives.value = amount
    }


    /**
     * This function "spins" the wheel
     */
    fun spinWheel() : String {
        val rand = (0.. spinWheelTypes.values().size-1).random()
        val wheelResult = spinWheelTypes.values()[rand]

        when (wheelResult){
            spinWheelTypes.INCREASE_SCORE -> changeScore(FALSE)
            spinWheelTypes.BANKRUPT       -> changeScore(TRUE)
            spinWheelTypes.INCREASE_LIFE -> changeUserLife(1)
            spinWheelTypes.DECREASE_LIFE  -> changeUserLife(-1)
        }

        return wheelResult.description
    }

    /**
     * If user has guessed correct letter, replaces the right - in the word
     */
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

/**
 * Types of categories on the wheel
 */
enum class spinWheelTypes(val description : String){
    INCREASE_SCORE("Du får flere point!"),
    INCREASE_LIFE ("Du har fået et ekstra liv"),
    BANKRUPT ("Du er gået fallit"),
    DECREASE_LIFE("Du har mistet et liv")
}





