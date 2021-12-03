package com.example.s152780_lykkehjulet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.s152780_lykkehjulet.databinding.FragmentHighScoresBinding
//TODO: Vis dummy data på skærmen over highscores som en scrolalble list
//TODO: Skal bruge recyclerview til at vise highscores
//TODO: SKal rydde lidt op
/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class WonGameFragment : Fragment() {

    private var visible: Boolean = false



    private var _binding: FragmentHighScoresBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHighScoresBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playAgain.setOnClickListener {findNavController().navigate(R.id.action_highScores_to_FirstFragment)}
        binding.ExitGame.setOnClickListener { activity?.finish() }

        visible = true

    }
}