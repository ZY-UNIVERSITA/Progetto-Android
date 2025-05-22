package com.zyuniversita.ui.main.mainactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zyuniversita.ui.databinding.HomepageActivityBinding
import com.zyuniversita.ui.main.mainactivity.mainenum.ApplicationActivityFactory
import com.zyuniversita.ui.main.mainactivity.mainenum.ApplicationFragmentFactory
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG: String = "Home Page TAG"
    }

    private lateinit var _binding: HomepageActivityBinding

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "Activity created")

        _binding = HomepageActivityBinding.inflate(layoutInflater)
        val view = _binding.root

        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                // add the frame using the parameters
                // id of the containerFragment of the activity in which the fragment will be loaded and the class of the fragment
                val navFragment = ApplicationFragmentFactory.getPageClass(Page.NAV).simpleName
                add(_binding.fragmentContainerNav.id, ApplicationFragmentFactory.getPage(Page.NAV), navFragment)

                val mainFragment = ApplicationFragmentFactory.getPageClass(Page.HOME).simpleName
                add(_binding.fragmentContainerMain.id, ApplicationFragmentFactory.getPage(Page.HOME), mainFragment)

                // Optimize the process of transitions between fragments
                setReorderingAllowed(true)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentFragment.collect { fragment ->
                    // create a fragment tag
                    val fragmentTag = ApplicationFragmentFactory.getPageClass(fragment).simpleName

                    // find a fragment using the tag
                    val existingFragment = supportFragmentManager.findFragmentByTag(fragmentTag)

                    // find the number of fragment in the fragments stack
                    val fragmentCount: Int = supportFragmentManager.fragments.size

                    println("Il fragment $existingFragment esiste? ${existingFragment != null}")

                    supportFragmentManager.commit {
                        setReorderingAllowed(true)

                        if (existingFragment == null) {
                            println("il fragment non esiste")
                            val navPageFragment = ApplicationFragmentFactory.getPage(fragment)

                            replace(_binding.fragmentContainerMain.id, navPageFragment, fragmentTag)
                        } else {
                            println("il fragment esiste")
                            replace(
                                _binding.fragmentContainerMain.id,
                                existingFragment,
                                fragmentTag
                            )
                        }

                        addToBackStack(fragmentTag)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentActivity.collect { newActivity ->
                    nextActivity(newActivity)
                }
            }

        }
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "Activity in pause")

    }

//    private fun fragmentClassFactory(fragmentName: String): Class<out Fragment> {
//        return when (fragmentName.lowercase()) {
//            "home" -> HomePageFragment::class.java
//            "game" -> GamesLanguageListFragment::class.java
//            "theory" -> LanguageListFragment::class.java
//            "theory_words" -> WordsListFragment::class.java
//            "single_word" -> SingleWordFragment::class.java
//            "find_character" -> FindCharacterFragment::class.java
//            "profile" -> ProfileFragment::class.java
//            "choose_game" -> GameChoosingFragment::class.java
//            "choose_words" -> WordsChoosingFragment::class.java
//            "nav" -> NavigationFragment::class.java
//            else -> throw IllegalArgumentException("Page not supported")
//        }
//    }


//    private fun fragmentFactory(page: String): Fragment {
//        return when (page.lowercase()) {
//            "home" -> HomePageFragment()
//            "game" -> GamesLanguageListFragment()
//            "theory" -> LanguageListFragment()
//            "theory_words" -> WordsListFragment()
//            "single_word" -> SingleWordFragment()
//            "find_character" -> FindCharacterFragment()
//            "profile" -> ProfileFragment()
//            "choose_game" -> GameChoosingFragment()
//            "choose_words" -> WordsChoosingFragment()
//            "nav" -> NavigationFragment()
//            else -> throw IllegalArgumentException("Page not supported")
//        }
//    }

    private fun nextActivity(page: Page) {
        val intent = Intent(this, ApplicationActivityFactory.getPageClass(page)).apply {
            putExtra("extra", page)
        }

        startActivity(intent)
    }

//    private fun gameActivityFactory(activityName: String): Class<out AppCompatActivity> {
//        return when (activityName.lowercase()) {
//            "eng_lang_game", "writing_game" -> GameActivity::class.java
//            "settings" -> SettingsActivity::class.java
//            else -> throw IllegalArgumentException("Not a valid activity")
//        }
//    }
}