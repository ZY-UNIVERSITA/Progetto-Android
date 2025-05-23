package com.zyuniversita.ui.main.game.words_choosing

import android.os.Bundle
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
import com.zyuniversita.ui.databinding.WordsChoosingFragmentBinding
import com.zyuniversita.ui.main.game.words_choosing.uistate.WordChoosingEvent
import com.zyuniversita.ui.main.mainactivity.MainActivityViewModel
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WordsChoosingFragment : Fragment() {
    private var _binding: WordsChoosingFragmentBinding? = null
    private val binding get() = _binding!!

    private val TAG: String = "Choose words fragment TAG"

    private val viewModel: WordsChoosingViewModel by viewModels()

    private val activityViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var leveListAdapter: LevelListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "Fragment created")

        _binding = WordsChoosingFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        leveListAdapter = LevelListAdapter(mutableListOf(), viewModel::changeWordSelection)

        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = leveListAdapter
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchData()

        binding.nextButton.setOnClickListener {
            viewModel.updateUserDataWordSelection()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                changeFragment()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                updateList()
            }
        }
    }

    private suspend fun changeFragment() {
        viewModel.uiEvent.collect { event ->
            when (event) {
                WordChoosingEvent.NavigateToGamePage -> activityViewModel.changeFragment(Page.CHOOSE_GAME)
            }
        }
    }

    private suspend fun updateList() {
        viewModel.wordsList.collect { list ->
            (binding.recycleView.adapter as? LevelListAdapter)?.updateList(list)
        }
    }
}