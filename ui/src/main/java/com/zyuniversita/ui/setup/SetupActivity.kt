package com.zyuniversita.ui.setup

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zyuniversita.ui.databinding.ActivitySetupBinding
import com.zyuniversita.ui.main.mainactivity.MainActivity
import com.zyuniversita.ui.main.mainactivity.mainenum.ApplicationFragmentFactory
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import com.zyuniversita.ui.setup.uistate.SetupEvent
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

        _binding.progressBar.setProgress(0, true)

        collectProgressBar()

        collectEvents()
    }

    /* ---------- One-Shot Event Collection ---------- */
    private fun collectProgressBar() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { progress ->
                    if (progress.percentage > 0) {
                        _binding.progressBar.visibility = View.VISIBLE
                        _binding.overlayView.visibility = View.VISIBLE
                    }

                    animateProgressBar(progress.percentage)
                }
            }
        }
    }

    private fun animateProgressBar(newValue: Int) {
        val progressBar = _binding.progressBar
        val animator = ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, newValue)
        animator.duration = 500
        animator.start()
    }

    private fun collectEvents() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.event.collect { event ->
                when (event) {
                    SetupEvent.NavigateToHome -> {
                        delay(1000)
                        goToHomePage()
                    }

                    SetupEvent.NavigateToRegister -> changeFragment(Page.REGISTER)
                    SetupEvent.NavigateToLogin -> changeFragment(Page.LOGIN)
                    SetupEvent.NavigateToLocalRegister -> changeFragment(Page.LOCAL_REGISTER)
                }
            }
        }
    }

    /* ---------- Navigation ---------- */

    private fun goToHomePage() {
        val goToTheHome = Intent(this, MainActivity::class.java)
        startActivity(goToTheHome)

        finish()
    }

    private fun changeFragment(page: Page) {
        supportFragmentManager.commit {
            val navFragment = ApplicationFragmentFactory.getPageClass(page).simpleName
            replace(
                _binding.fragmentContainerView.id,
                ApplicationFragmentFactory.getPage(page),
                navFragment
            )

            setReorderingAllowed(true)
        }
    }
}
