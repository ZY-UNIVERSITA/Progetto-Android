package com.zyuniversita.ui.games.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.zyuniversita.ui.databinding.ActivityGameBinding
import com.zyuniversita.ui.games.main.uistate.GameEvent
import com.zyuniversita.ui.main.mainactivity.MainActivity
import com.zyuniversita.ui.main.mainactivity.mainenum.ApplicationFragmentFactory
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameActivity() : AppCompatActivity() {
    private lateinit var _binding: ActivityGameBinding

    companion object {
        private const val TAG: String = "Game Activity TAG"
    }

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")

        _binding = ActivityGameBinding.inflate(layoutInflater)

        val view = _binding.root

        setContentView(view)

        createGame()

        lifecycleScope.launch {
            viewModel.gameEvent.collect { event ->
                when (event) {
                    GameEvent.GameFinished -> finishGame()
                }
            }
        }
    }

    private fun createGame() {
        val gameName: Page? = intent.getSerializableExtra("extra", Page::class.java)

        gameName?.let {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(_binding.fragmentContainerView.id, ApplicationFragmentFactory.getPage(gameName))
            }
        }

    }

//    private fun fragmentFactory(page: String): Fragment {
//        return when (page.lowercase()) {
//            "eng_lang_game" -> MultipleChoiceGameFragment()
//            "writing_game" -> WritingGameFragment()
//            else -> throw IllegalArgumentException("Game not supported")
//        }
//    }

    private fun finishGame() {
        lifecycleScope.launch {
            Toast.makeText(
                this@GameActivity,
                "Exercises have been terminated. You will soon return to the homepage.",
                Toast.LENGTH_LONG
            ).show()

            val intent = Intent(this@GameActivity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            }
            startActivity(intent)

            finish()
        }
    }
}