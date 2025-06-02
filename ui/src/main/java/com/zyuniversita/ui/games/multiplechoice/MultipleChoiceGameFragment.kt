package com.zyuniversita.ui.games.multiplechoice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zyuniversita.domain.model.words.MultipleChoiceQuestion
import com.zyuniversita.ui.databinding.FragmentMultipleChoiceGameBinding
import com.zyuniversita.ui.games.main.GameViewModel
import com.zyuniversita.ui.games.writing.uistate.GameEvents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MultipleChoiceGameFragment : Fragment() {
    private var _binding: FragmentMultipleChoiceGameBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG: String = "Multiple choice fragment TAG"
    }

    private val viewModel: MultipleChoiceGameViewModel by viewModels()

    private val activityViewModel: GameViewModel by activityViewModels()

    private lateinit var listButton: List<Button>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d(TAG, "Fragment created")

        _binding = FragmentMultipleChoiceGameBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listButton = listOf(
            binding.buttonTopLeft,
            binding.buttonTopRight,
            binding.buttonBottomLeft,
            binding.buttonBottomRight
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        GameEvents.DataLoaded -> {
                            enableButton()
                            enableNextButton()
                            viewModel.generateQuestion()
                        }

                        GameEvents.NewWord -> {
                            randomQuestion()
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


    private fun enableButton() {
        listButton.forEach {
            it.isEnabled = true
        }

        binding.terminateButton.setOnClickListener {
            finishGame()
        }
    }

    private fun enableNextButton() {
        binding.nextButton.isEnabled = true

        binding.nextButton.setOnClickListener {
            lifecycleScope.launch {
                enableButton()
                viewModel.generateQuestion()
            }
        }
    }

    private fun disableNextButton() {
        binding.nextButton.setOnClickListener(null)
        binding.nextButton.isEnabled = false
    }

    private fun randomQuestion() {
        val randomWord: MultipleChoiceQuestion? = viewModel.word

        disableNextButton()

        randomWord?.let {
            binding.textView.text = randomWord.question.word.word

            for (i in listButton.indices) {
                listButton[i].setOnClickListener(null)

                listButton[i].text = randomWord.answers[i].word.meaning

                listButton[i].setOnClickListener {
                    if (i == randomWord.solution) {
                        Toast.makeText(requireContext(), "Oki!", Toast.LENGTH_SHORT).show()
                        viewModel.tempUpdate(true)
                    } else {
                        Toast.makeText(requireContext(), "Not so Oki!", Toast.LENGTH_SHORT).show()
                        viewModel.tempUpdate(false)
                    }

                    listButton.forEach { it.isEnabled = false }
                    enableNextButton()
                }
            }
        } ?: finishGame()
    }

    private fun finishGame() {
        viewModel.saveSession()
    }

    private fun returnToHome() {
        activityViewModel.finishGame()
    }
}