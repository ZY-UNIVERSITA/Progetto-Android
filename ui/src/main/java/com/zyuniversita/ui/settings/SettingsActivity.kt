package com.zyuniversita.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.zyuniversita.domain.model.settings.SettingsToSave
import com.zyuniversita.ui.databinding.ActivitySettingsBinding
import com.zyuniversita.ui.settings.uistate.SettingsEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsActivity() : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    private val TAG: String = "Settings_Activity_TAG"

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")

        binding = ActivitySettingsBinding.inflate(layoutInflater)

        val view = binding.root

        viewModel.fetchAllData()

        setContentView(view)

        collectAllData()

        binding.Home.setOnClickListener {
            goToHomePage()
        }

        binding.Save.setOnClickListener {
            val username = binding.username.text.toString()
            val repetition = binding.repeatWords.isChecked
            val synchronization = binding.synchronization.isChecked
            viewModel.saveData(SettingsToSave(username, repetition, synchronization))
        }
    }

    private fun collectAllData() {
        collectUsername()
        collectRepetition()
        collectUiEvent()
    }

    private fun collectUsername() {
        lifecycleScope.launch {
            viewModel.username.filterNotNull().collect { username ->
                binding.username.setText(username)
            }

        }
    }

    private fun collectRepetition() {
        lifecycleScope.launch {
            viewModel.wordRepetition.filterNotNull().collect { repetition ->
                binding.repeatWords.isChecked = repetition
            }

        }
    }

    private fun collectUiEvent() {
        lifecycleScope.launch {
            viewModel.settingsEvent.collect { event ->
                when (event) {
                    SettingsEvent.goToHome -> goToHomePage()
                }
            }
        }

    }

    private fun goToHomePage() {
        finish()
    }
}