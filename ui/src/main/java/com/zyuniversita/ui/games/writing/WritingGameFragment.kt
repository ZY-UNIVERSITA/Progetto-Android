package com.zyuniversita.ui.games.writing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zyuniversita.ui.databinding.FragmentWritingGameBinding
import com.zyuniversita.ui.games.main.GameViewModel
import com.zyuniversita.ui.games.writing.uistate.GameEvents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WritingGameFragment : Fragment() {
    private var _binding: FragmentWritingGameBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG: String = "Writing game fragment TAG"
    }

    private val viewModel: WritingGameViewModel by viewModels()

    private val activityViewModel: GameViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d(TAG, "Fragment created")

        _binding = FragmentWritingGameBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        GameEvents.DataLoaded -> {
                            setupListeners()
                            viewModel.generateQuestion()
                        }

                        GameEvents.NewWord -> {
                            loadNextWord()
                        }

                        GameEvents.GameEnded -> {
                            returnToHome()
                        }
                    }
                }
            }
        }

        viewModel.fetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Fragment destroyed")

        _binding = null
    }

    private fun setupListeners() = with(binding) {

        // 0) Exit
        exitButton.setOnClickListener { finishGame() }

        // 1) Show solution
        showResult.setOnClickListener {
            answerQuizText.visibility = View.VISIBLE
            correctButton.isEnabled = true
            incorrectButton.isEnabled = true
            nextWordButton.isEnabled = false
        }

        // 2) Scelta risposta
        correctButton.setOnClickListener { handleAnswer(isCorrect = true) }
        incorrectButton.setOnClickListener { handleAnswer(isCorrect = false) }

        // 3) Next word
        nextWordButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.generateQuestion()
                }
            }
        }

        // 4) Clear drawing
        clearButton.setOnClickListener {
            drawingView.clearAllStrokes()
            Toast.makeText(
                requireContext(),
                "Clear eseguito", Toast.LENGTH_SHORT
            ).show()
        }

        // 5) Save drawing  (placeholder: al momento facciamo l’undo, vedi nota più sotto)
        undoButton.setOnClickListener {
            // TODO: sostituisci con la tua logica di salvataggio reale
            drawingView.undoStroke()          // <-- demo / placeholder
            Toast.makeText(
                requireContext(),
                "Undo eseguito", Toast.LENGTH_SHORT
            ).show()
        }
    }

    /* -------------------------------------------------------------------- */
    /* -----------------------------  UI  --------------------------------- */
    /* -------------------------------------------------------------------- */
    private fun resetUI() = with(binding) {
        // visibilità / abilita-zione
        answerQuizText.visibility = View.GONE
        showResult.isEnabled = true

        correctButton.isEnabled = false
        incorrectButton.isEnabled = false
        nextWordButton.isEnabled = false

        // pulizia area disegno
        drawingView.clearAllStrokes()
    }

    private fun handleAnswer(isCorrect: Boolean) {
        Log.d(TAG, "Utente ha indicato: $isCorrect")

        with (binding) {
            // attiva “next”, disattiva di nuovo correct / incorrect
            nextWordButton.isEnabled = true
            correctButton.isEnabled = false
            incorrectButton.isEnabled = false
        }

        viewModel.tempUpdate(isCorrect)
    }

    private suspend fun loadNextWord() = with(binding) {
        resetUI()

        val next = viewModel.word

        next?.let {
            quizWordText.text = next.word.meaning
            answerQuizText.text = next.word.word
            transliterationText.text = next.word.transliteration
        } ?: finishGame()
    }

    private fun finishGame() {
        viewModel.saveSession()
    }

    private fun returnToHome() {
        activityViewModel.finishGame()
    }
}
