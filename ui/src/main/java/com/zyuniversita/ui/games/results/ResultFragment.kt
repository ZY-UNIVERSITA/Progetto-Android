package com.zyuniversita.ui.games.results

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.zyuniversita.ui.databinding.FragmentGameResultsBinding
import com.zyuniversita.ui.games.main.GameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment: Fragment() {
    private var _binding: FragmentGameResultsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG: String = "Results fragment TAG"
    }

    private val viewModel: ResultsViewModel by viewModels()

    private val activityViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d(TAG, "Fragment created")

        _binding = FragmentGameResultsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateWordsStats(activityViewModel.wordsStats.toMutableMap())

        with(binding) {
            "Total answers: ${viewModel.totalQuestions}".also { totalQuestions.text = it }
            "Correct: ${viewModel.correctQuestions}".also { correctText.text = it }
            "Incorrect: ${viewModel.incorrectQuestions}".also { incorrectText.text = it }

            binding.homeButton.setOnClickListener {
                activityViewModel.finishGame()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Fragment destroyed")

        _binding = null
    }
}