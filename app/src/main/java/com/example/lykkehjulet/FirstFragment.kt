package com.example.lykkehjulet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lykkehjulet.databinding.FragmentFirstBinding
import com.example.lykkehjulet.model.ViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

//TODO: Skal vise eller holde styr på hvilke bogstaver der er gættet på
//TODO: Skal have selve lykkehjul elementet med
//TODO: Skal vise kategorien af ord
//TODO: Implmenter spinning wheel
//TODO: Skal vise kategorien på startsiden
/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val word : String = binding.textField.text.toString()

            if(viewModel.isUserWordCorrect(word)){
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }

        binding.buttonSecond.setOnClickListener {
            val word : String = binding.textField.text.toString()
            if(word.isNotEmpty() && word.length == 1){

            val letter : Char = word[0]
                println(viewModel.isUserLetterCorrect(letter))
            }
            viewModel.increaseScore(10)
            //viewModel.initGuessWord()


            update()

        }
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




        binding.spinwheel.setOnClickListener {
        val snack = Snackbar.make(view, "this is a sample", Snackbar.LENGTH_LONG)
            snack.show()


        }


    }
    private fun update(){
        ("Antal liv: " + viewModel.lives.value.toString()).also { binding.lives.text = it }
        ("Score: " + viewModel.score.value.toString()).also { binding.score.text = it }
        binding.currentword.text = viewModel.guessWord
    }

    //TODO: Implement
    /*
    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, viewModel.score.value))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()
    }
     */


    private fun restartGame() {
        viewModel.reinitializeData()
        //setErrorTextField(false)
    }

    private fun exitGame() {
        activity?.finish()
    }

}