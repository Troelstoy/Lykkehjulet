package com.example.lykkehjulet

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lykkehjulet.databinding.FragmentGameBinding
import com.example.lykkehjulet.model.ViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

//TODO: Tilføj logik der checker om input er validt
//TODO: Vis en warning når der ikke bliver givet rigtigt input i gæt
//TODO: Genstarter ikke spillet ordentligt
/**
 * A [Fragment] subclass as the default destination in the navigation.
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

        binding.guessWordButton.setOnClickListener {
            val word : String = binding.textField.text.toString()

            //findNavController().navigate(R.id.action_FirstFragment_to_highScores)

            if(word.isNotEmpty()){

            if(viewModel.isUserWordCorrect(word)){
                showFinalScoreDialog()
                }
            _isGuessingTime = TRUE
            update()
            }
        }

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

        binding.category.text = "Kategori: " + viewModel.currentCategoryString
        /**
         * Indlæser det rigtige ord
         */
        viewModel.initGuessWord()
        binding.currentword.text = viewModel.guessWord


        /**
         * Dummy måde at indsætte et tal på
         */
        ("Antal liv: " + viewModel.lives.value.toString()).also { binding.lives.text = it }
        ("Score: " + viewModel.score.value.toString()).also { binding.score.text = it }

        update()
    }

    private fun update(){
        ("Antal liv: " + viewModel.lives.value.toString()).also { binding.lives.text = it }
        ("Score: " + viewModel.score.value.toString()).also { binding.score.text = it }
        binding.currentword.text = viewModel.guessWord
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


    @SuppressLint("StringFormatInvalid")
    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.wongame))
                //TODO: Doesnt display score :(
            .setMessage(getString(R.string.you_scored, viewModel.score.value.toString()) )
            .setCancelable(false)
            .setNegativeButton(getString(R.string.show_highscores)) { _, _ ->
                findNavController().navigate(R.id.action_FirstFragment_to_highScores)
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()
    }




    //TODO: Virker ikke ordentligt
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