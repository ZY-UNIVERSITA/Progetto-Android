package com.zyuniversita.ui.main.game.games_choosing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zyuniversita.ui.databinding.FragmentGameChoosingBinding
import com.zyuniversita.ui.main.game.games_choosing.uistate.GameChoosingEvent
import com.zyuniversita.ui.main.mainactivity.MainActivityViewModel
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameChoosingFragment : Fragment() {
    private var _binding: FragmentGameChoosingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameChoosingViewModel by viewModels()

    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameChoosingBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loadNextPage()
            }
        }

        binding.engLangImage.setOnClickListener {
            loadNextData(Page.GAME_MULTIPLE_CHOOSING)
        }

        binding.langEngImage.setOnClickListener {
            loadNextData(Page.GAME_MULTIPLE_CHOOSING_INV)
        }

        binding.writingGameImage.setOnClickListener {
            loadNextData(Page.GAME_WRITING)
        }

        binding.linkingGameImage.setOnClickListener {
            loadNextData(Page.GAME_LINKING)
        }
    }

    private fun loadNextData(nextPage: Page) {
        viewModel.loadNextData(activityViewModel.currentLanguage, nextPage)
    }

    private suspend fun loadNextPage() {
        viewModel.uiState.collect { event ->
            when (event) {
                is GameChoosingEvent.GamePage -> activityViewModel.changeActivity(event.nextFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}