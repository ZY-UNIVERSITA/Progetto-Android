package com.zyuniversita.ui.setup

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        private const val POST_NOTIFICATION_PERMISSION: String =
            Manifest.permission.POST_NOTIFICATIONS
    }


    private val viewModel: SetupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")

        _binding = ActivitySetupBinding.inflate(layoutInflater)

        val view = _binding.root

        setContentView(view)

        notificationPermission()


    }

    /* ---------- One-Shot Event Collection ---------- */
    private fun collectProgressBar() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { progress ->
                    if (progress.percentage > 0 && !progress.isUserIdMissing) {
                        _binding.progressBar.visibility = View.VISIBLE
                        _binding.fragmentContainerView.visibility = View.GONE
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
//                        _binding.progressBar.visibility = View.VISIBLE
//                        animateProgressBar(100)
                        delay(1000)
                        goToHomePage()
                    }

                    SetupEvent.NavigateToRegister -> {
                        changeFragment(Page.REGISTER)
                        _binding.progressBar.visibility = View.GONE
                    }

                    SetupEvent.NavigateToLogin -> {
                        changeFragment(Page.LOGIN)
                        _binding.progressBar.visibility = View.GONE

                    }

                    SetupEvent.NavigateToLocalRegister -> {
                        changeFragment(Page.LOCAL_REGISTER)
                        _binding.progressBar.visibility = View.GONE
                    }
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

    private fun execAfterPermission() {
        viewModel.navigation()
        viewModel.fetchUsername()
        viewModel.checkDatabase()
        viewModel.loadLanguage()

        _binding.progressBar.setProgress(0, true)

        collectProgressBar()

        collectEvents()
    }

    // check notification permission
    private fun notificationPermission() {
        // permesso concesso
        if (ContextCompat.checkSelfPermission(
                this,
                POST_NOTIFICATION_PERMISSION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.startNotificationWorker()
            execAfterPermission()

            // permesso non concesso, mostra razionale
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                POST_NOTIFICATION_PERMISSION
            )
        ) {
            viewModel.removeNotificationWorker()

            showRationaleDialog()

            // permesso non concesso o mai richiesto
        } else {
            viewModel.removeNotificationWorker()
            notificationPermissionLauncher.launch(POST_NOTIFICATION_PERMISSION)
        }
    }


    // show user options to choose for the permission
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.startNotificationWorker()
            } else {
                Toast.makeText(
                    this,
                    "Notification denied. You cannot use this function.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            execAfterPermission()
        }

    // show rationale
    private fun showRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle("The permission is required")
            .setMessage("Without this permission, we cannot show you any notification..")
            .setPositiveButton("OK") { _, _ ->
                notificationPermissionLauncher.launch(POST_NOTIFICATION_PERMISSION)
            }
            .setNegativeButton("No") {
                // esegue il resto delle funzioni se l'utente spinge no
                    _, _ ->
                execAfterPermission()
            }.setOnCancelListener {
                // esegue il resto delle funzioni se l'utente spinge il tasto back
                execAfterPermission()
            }
            .show()
    }
}
