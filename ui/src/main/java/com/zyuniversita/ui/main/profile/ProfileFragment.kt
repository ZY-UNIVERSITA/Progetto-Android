package com.zyuniversita.ui.main.profile

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
import com.zyuniversita.domain.model.userdata.GeneralUserStats
import com.zyuniversita.ui.R
import com.zyuniversita.ui.databinding.FragmentProfileBinding
import com.zyuniversita.ui.main.mainactivity.MainActivityViewModel
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private val activityViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var profileAdapter: ProfileAdapter

    companion object {
        private const val TAG = "ProfileFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchUserId()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d(TAG, "Fragment created")

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        profileAdapter = ProfileAdapter(mutableListOf())

        binding.recyclerLanguages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = profileAdapter
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchUserData()
        viewModel.fetchUsername()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userData.collect { statsList  ->
                    val completedQuiz: String = statsList .sumOf { it.completedQuiz }.toString()
                    binding.completedQuiz.text = getString(R.string.quiz, completedQuiz)

                    val correct = statsList .sumOf { it.correctAnswer }
                    val wrong = statsList .sumOf { it.wrongAnswer }
                    val totalAnswers = correct + wrong

                    val percentage = if (totalAnswers == 0) 0.0 else (correct.toDouble() / totalAnswers * 100)

                    binding.percentageCorrect.text = getString(R.string.correct, percentage)

                    updateList(statsList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.username.filterNotNull().collect { username ->
                    binding.textUsername.text = username
                }
            }
        }

        binding.buttonEditProfile.setOnClickListener {
            lifecycleScope.launch {
                activityViewModel.changeActivity(Page.SETTINGS)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Fragment destroyed")

        _binding = null
    }

    private fun updateList(newList: List<GeneralUserStats>) {
        profileAdapter.updateList(newList)
    }
}