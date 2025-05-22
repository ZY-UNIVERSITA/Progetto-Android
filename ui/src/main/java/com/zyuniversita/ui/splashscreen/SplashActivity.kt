package com.zyuniversita.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.zyuniversita.ui.databinding.ActivityMainBinding
import com.zyuniversita.ui.setup.SetupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    companion object {
        private const val TAG: String = "Splash_Activity_TAG"
    }

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            delay(2500)
            goToSetupPage()
        }
    }

    private fun goToSetupPage() {
        val setupPage = Intent(this, SetupActivity::class.java)
        startActivity(setupPage)

        finish()
    }

}