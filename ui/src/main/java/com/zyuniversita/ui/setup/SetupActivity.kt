package com.zyuniversita.ui.setup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zyuniversita.ui.databinding.ActivitySetupBinding
import com.zyuniversita.ui.main.mainactivity.MainActivity
import com.zyuniversita.ui.setup.uistate.SetupEvent
import com.zyuniversita.ui.setup.uistate.SetupUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SetupActivity() : AppCompatActivity() {
    private lateinit var _binding: ActivitySetupBinding

    companion object {
        private const val TAG: String = "Setup_Activity_TAG"
    }

    private val viewModel: SetupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")

        _binding = ActivitySetupBinding.inflate(layoutInflater)

        val view = _binding.root

        setContentView(view)

        viewModel.startNotificationWorker()

        viewModel.navigation()
        viewModel.fetchUsername()
        viewModel.checkDatabase()
        viewModel.loadLanguage()

        collectUiState()
        collectEvents()
        initListeners()

        _binding.progressBar.setProgress(0, true)
    }

     /* ---------- UI State Collection and Rendering ---------- */

    private fun collectUiState() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiState.collect {
                render(it)
            }
        }
    }

    private fun render(state: SetupUiState) = with(_binding) {

        if (state.isUsernameMissing) {
            // First time visit: set username and enter button
            setUsername.isVisible = true
            enterButton.isVisible = true

        } else {
            // Otherwise: everything is hidden
            setUsername.isGone = true
            enterButton.isGone = true
            usernameSetting.isGone = true

            // Loading text
            progressBar.isVisible = true
        }

        _binding.progressBar.setProgress(state.percentage, true)
    }

    /* ---------- One-Shot Event Collection ---------- */

    private fun collectEvents() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.event.collect { event ->
                delay(2000)
                when (event) {
                    SetupEvent.NavigateToHome -> goToHomePage()
                }
            }
        }
    }

    /* ---------- Listener Initialization (User Interaction) ---------- */

    private fun initListeners() = with(_binding) {

        // The enter button is enabled only when the username is not empty
        setUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                enterButton.isEnabled = !s.isNullOrBlank()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Button listener to send username to the view model
        enterButton.setOnClickListener {
            viewModel.onUsernameConfirmed(setUsername.text.toString())
        }
    }

    /* ---------- Navigation ---------- */

    private fun goToHomePage() {
        val goToTheHome = Intent(this, MainActivity::class.java)
        startActivity(goToTheHome)

        finish()
    }
}
