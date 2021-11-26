package com.example.lykkehjulet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lykkehjulet.databinding.GamefragmentBinding
import com.example.lykkehjulet.model.ViewModel
import com.google.android.material.snackbar.Snackbar
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

//TODO: Skal vise eller holde styr på hvilke bogstaver der er gættet på
//TODO: Skal have selve lykkehjul elementet med
//TODO: Implmenter spinning wheel
//TODO: Tilføj logik der checker om input er validt
//TODO: Kan ikke navigere til higscores skærmen
//TODO: Vis en warning når der ikke bliver givet rigtigt input i gæt
/**
 * A [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {

    private var _isSpinningTime = FALSE

    private lateinit var binding: GamefragmentBinding
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.gamefragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val word : String = binding.textField.text.toString()

            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

            //TODO: Change back
            /*
            if(viewModel.isUserWordCorrect(word)){
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
            setVisibilitySpin()
            update()
             */
        }

        binding.buttonSecond.setOnClickListener {

            if(_isSpinningTime){
                binding.buttonSecond.text = "Gæt Bogstav"

                val snack = Snackbar.make(view, viewModel.spinWheel(), Snackbar.LENGTH_LONG)
                snack.show()
                update()
                _isSpinningTime = FALSE
                update()


                println("DU har lige spunnet hjulet")
                println(_isSpinningTime)


            }else {
                binding.buttonSecond.text = "Spin the wheel"
                val word: String = binding.textField.text.toString()

                if (word.isNotEmpty() && word.length == 1) {

                    val letter: Char = word.first()
                    println(viewModel.isUserLetterCorrect(letter))

                }
                    _isSpinningTime = TRUE
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


        /**
         * Button for spinning the wheel
         */
        binding.spinwheel.setOnClickListener {





        }
        update()
    }

    private fun update(){
        ("Antal liv: " + viewModel.lives.value.toString()).also { binding.lives.text = it }
        ("Score: " + viewModel.score.value.toString()).also { binding.score.text = it }
        binding.currentword.text = viewModel.guessWord
        isGameOver()


        if (_isSpinningTime){
            binding.buttonFirst.visibility = View.INVISIBLE
            binding.buttonSecond.visibility = View.VISIBLE
            binding.spinwheel.visibility = View.INVISIBLE
        }else {
            binding.buttonFirst.visibility = View.VISIBLE
            binding.buttonSecond.visibility = View.VISIBLE
            binding.spinwheel.visibility = View.INVISIBLE
        }
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

    private fun isGameOver(){
        if(viewModel.lives.value!! <= 0){
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        if(viewModel.wordisGuessed()){
            println("Du har vundet")
            findNavController().navigate(R.id.action_FirstFragment_to_highScores)
        }


    }

}