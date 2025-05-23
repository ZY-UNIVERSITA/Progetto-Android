package com.zyuniversita.ui.main.game.games_language

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
import com.zyuniversita.ui.databinding.LanguageListFragmentBinding
import com.zyuniversita.ui.main.mainactivity.MainActivityViewModel
import com.zyuniversita.ui.main.game.games_language.uistate.LanguageChoosingEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GamesLanguageListFragment : Fragment() {
    private var _binding: LanguageListFragmentBinding? = null
    private val binding get() = _binding!!

    private val TAG: String = "Choose language fragment TAG"

    private val viewModel: GamesLanguageListViewModel by viewModels()

    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "Fragment created")

        _binding = LanguageListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        // Load available language
        viewModel.fetchData()

        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.adapter = GamesLanguageListAdapter(
            mutableListOf(),
            viewModel::selectLanguage,
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                updateList()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                changeFragment()
            }
        }
    }

    private suspend fun updateList() {
        viewModel.availableLanguage.collect { value ->
            (binding.recycleView.adapter as? GamesLanguageListAdapter)?.updateList(value.toMutableList())
        }
    }

    private suspend fun changeFragment() {
        viewModel.uiEvent.collect { event ->
            activityViewModel.changeLanguage(viewModel.language)

            when (event) {
                LanguageChoosingEvent.GameChoosingPage -> activityViewModel.changeFragment(viewModel.nextFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Fragment destroyed")

        _binding = null
    }

}