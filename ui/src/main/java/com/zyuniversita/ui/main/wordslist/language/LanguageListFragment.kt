package com.zyuniversita.ui.main.wordslist.language

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
import com.zyuniversita.domain.model.words.AvailableLanguage
import com.zyuniversita.ui.databinding.FragmentLanguageListForWordsListBinding
import com.zyuniversita.ui.main.mainactivity.MainActivityViewModel
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import com.zyuniversita.ui.main.wordslist.language.uistate.LanguageListEvent
import com.zyuniversita.ui.utils.mapper.FlagMapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LanguageListFragment : Fragment() {
    private var _binding: FragmentLanguageListForWordsListBinding? = null
    private val binding get() = _binding!!

    private val TAG: String = "Profile fragment TAG"

    private val viewModel: LanguageListViewModel by viewModels()

    private val activityViewModel: MainActivityViewModel by activityViewModels<MainActivityViewModel>()

    private lateinit var flagMapper: FlagMapper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "Fragment created")

        _binding = FragmentLanguageListForWordsListBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.adapter = LanguageListAdapter(
            mutableListOf(),
            viewModel::selectLanguage,
            flagMapper
        )

        viewModel.fetchData()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.languageList.collect { list ->
                    updateAdapter(list.toMutableList())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { event ->
                    when (event) {
                        LanguageListEvent.goToWordsList -> activityViewModel.changeFragment(Page.THEORY_WORDS)
                    }
                }
            }
        }

        binding.fabAdd.setOnClickListener {
            lifecycleScope.launch {
                activityViewModel.changeFragment(Page.FIND_CHARACTER)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Fragment destroyed")

        _binding = null
    }

    private fun updateAdapter(list: MutableList<AvailableLanguage>) {
        (binding.recycleView.adapter as? LanguageListAdapter)?.updateList(
            newList = list
        )
    }

    @Inject
    fun setFlagMapper(flagMapper: FlagMapper) {
        this.flagMapper = flagMapper
    }
}