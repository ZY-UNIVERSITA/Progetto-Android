package com.zyuniversita.ui.main.wordslist.words

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zyuniversita.domain.model.words.Word
import com.zyuniversita.ui.databinding.FragmentWordsListForWordsListBinding
import com.zyuniversita.ui.main.mainactivity.MainActivityViewModel
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import com.zyuniversita.ui.main.wordslist.words.uistate.WordsListEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WordsListFragment() : Fragment() {
    private var _binding: FragmentWordsListForWordsListBinding? = null
    private val binding get() = _binding!!

    private val TAG: String = "Profile fragment TAG"

    private val viewModel: WordsListViewModel by viewModels()

    private val activityViewModel: MainActivityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "Fragment created")

        _binding = FragmentWordsListForWordsListBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.adapter = WordsListLevelAdapter(
            mutableListOf(),
            viewModel::fetchSingleWord
        )

        viewModel.fetchData()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.wordsList.collect(::updateAdapter)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        WordsListEvent.goToWord -> changeFragment()
                    }
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val filter = s.toString()
                if (filter.isNotBlank()) {
                    viewModel.filterData(filter)
                } else {
                    viewModel.removeFilter()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Fragment destroyed")

        _binding = null
    }

    private suspend fun changeFragment() {
        activityViewModel.changeFragment(Page.SINGLE_WORD)
    }

    private fun updateAdapter(list: MutableList<MutableList<Word>>) {
        (binding.recycleView.adapter as? WordsListLevelAdapter)?.updateList(
            newList = list
        )
    }
}