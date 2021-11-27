package com.example.lykkehjulet

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lykkehjulet.databinding.FragmentSecondBinding
import com.example.lykkehjulet.model.ViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */


class LostGameFragment : Fragment() {

    private val viewModel: ViewModel by viewModels()

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            //findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            replayGameDialog()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("StringFormatInvalid")
    private fun replayGameDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.lossgame))
            .setMessage(getString(R.string.tease))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
               findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
            .show()
    }

    private fun exitGame() {
        activity?.finish()
    }
}