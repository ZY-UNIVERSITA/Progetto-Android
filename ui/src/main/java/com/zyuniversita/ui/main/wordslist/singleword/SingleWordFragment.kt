package com.zyuniversita.ui.main.wordslist.singleword

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zyuniversita.domain.model.words.WordProgress
import com.zyuniversita.ui.R
import com.zyuniversita.ui.databinding.FragmentSingleWordInformationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingleWordFragment(): Fragment() {
    private var _binding: FragmentSingleWordInformationBinding? = null
    private val binding get() = _binding!!

    private val TAG: String = "Profile fragment TAG"

    private val viewModel: SingleWordViewModel by viewModels<SingleWordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "Fragment created")

        _binding = FragmentSingleWordInformationBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.fetchData()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.singleWord.collect {
                    word -> updateUi(word)
                }
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Fragment destroyed")

        _binding = null
    }

    private fun updateUi(word: WordProgress) {
        binding.word.text = word.word.word
        binding.transliteration.text = word.word.transliteration
        binding.meaning.text = word.word.meaning
        binding.correct.text = getString(R.string.correct_answer, word.correct)
        binding.wrong.text = getString(R.string.wrong_answer, word.wrong)
    }
}