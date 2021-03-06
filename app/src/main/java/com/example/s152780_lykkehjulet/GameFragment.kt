package com.example.s152780_lykkehjulet

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.s152780_lykkehjulet.databinding.FragmentGameBinding
import com.example.s152780_lykkehjulet.model.ViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

//TODO: Kan tilføje logik der checker om input er validt
//TODO: Kan vise en warning når der ikke bliver givet rigtigt input i gæt f.eks. et ord der er gættet på
/**
 * A [Fragment] subclass as the default destination in the navigation.
 * Contains all game logic
 */
class GameFragment : Fragment() {

    private var _isGuessingTime = FALSE

    private lateinit var binding: FragmentGameBinding
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Logic for the guess word button
         */

        binding.guessWordButton.setOnClickListener {
            val word : String = binding.textField.text.toString()

            if(word.isNotEmpty()){

            if(viewModel.isUserWordCorrect(word)){
                showFinalScoreDialog()
                }
            _isGuessingTime = TRUE
            update()
            }
        }

        /**
         * Logic for the guess letter button
         */
        binding.guessLetterButton.setOnClickListener {

            if(_isGuessingTime){
                val snack = Snackbar.make(view, viewModel.spinWheel(), Snackbar.LENGTH_LONG)
                snack.show()
                update()
                _isGuessingTime = FALSE
                update()
            }
            else {
                val word: String = binding.textField.text.toString()

                if (word.isNotEmpty() && word.length == 1) {

                    val letter: Char = word.first()
                    viewModel.isUserLetterCorrect(letter)
                }
                    _isGuessingTime = TRUE
                update()
            }
        }
        update()
    }

    /**
     * Updates the screen and all updateables on it
     */

    private fun update(){
        ("Antal liv: " + viewModel.lives.value.toString()).also { binding.lives.text = it }
        ("Score: " + viewModel.score.value.toString()).also { binding.score.text = it }
        binding.currentword.text = viewModel.guessWord
        binding.category.text = viewModel.currentCategoryString
        isGameOver()


        if (_isGuessingTime){
            binding.guessWordButton.visibility = View.INVISIBLE
            binding.guessLetterButton.visibility = View.VISIBLE
            binding.guessLetterButton.text = "Spin the wheel"

        }else {
            binding.guessWordButton.visibility = View.VISIBLE
            binding.guessLetterButton.visibility = View.VISIBLE
            binding.guessLetterButton.text = "Gæt Bogstav"
        }
    }


    /**
     * Displays the wons/finalcore dialog
     */


    @SuppressLint("StringFormatInvalid")
    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.wongame))
            .setMessage(getString(R.string.you_scored) + " " + viewModel.score.value.toString())
            .setCancelable(false)
            .setNegativeButton(getString(R.string.show_highscores)) { _, _ ->
                findNavController().navigate(R.id.action_FirstFragment_to_highScores)
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()
    }


    /**
     * Used to restart the game
     */
    private fun restartGame() {
        viewModel.reinitializeData()
        update()
    }

    private fun exitGame() {
        activity?.finish()
    }

    private fun isGameOver(){
        if(viewModel.lives.value!! <= 0){
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        if(viewModel.wordisGuessed()){
            showFinalScoreDialog()
        }


    }

}