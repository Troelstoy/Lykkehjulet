package com.example.lykkehjulet

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lykkehjulet.databinding.FragmentHighScoresBinding
//TODO: Vis dummy data på skærmen over highscores som en scrolalble list
//TODO: Exit game knappen skal vise en besked hvor man er sikker på om man vil afslutte appen
//TODO: Play again skal redirecte til skærm 1
//TODO: Skal bruge recyclerview til at vise highscores
/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class WonGameFragment : Fragment() {

    private val showPart2Runnable = Runnable {
        // Delayed display of UI elements
        fullscreenContentControls?.visibility = View.VISIBLE
    }
    private var visible: Boolean = false


    private var dummyButton: Button? = null
    private var fullscreenContent: View? = null
    private var fullscreenContentControls: View? = null



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

        visible = true

    }

    override fun onResume() {
        super.onResume()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }

    override fun onPause() {
        super.onPause()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Clear the systemUiVisibility flag
        activity?.window?.decorView?.systemUiVisibility = 0

    }

    override fun onDestroy() {
        super.onDestroy()
        dummyButton = null
        fullscreenContent = null
        fullscreenContentControls = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}