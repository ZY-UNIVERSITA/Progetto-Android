package com.zyuniversita.ui.main.mainactivity.mainenum

import android.app.Activity
import androidx.fragment.app.Fragment
import com.zyuniversita.ui.findcharacter.FindCharacterFragment
import com.zyuniversita.ui.games.main.GameActivity
import com.zyuniversita.ui.games.multiplechoice.MultipleChoiceGameFragment
import com.zyuniversita.ui.games.writing.WritingGameFragment
import com.zyuniversita.ui.main.game.games_choosing.GameChoosingFragment
import com.zyuniversita.ui.main.game.games_language.GamesLanguageListFragment
import com.zyuniversita.ui.main.game.words_choosing.WordsChoosingFragment
import com.zyuniversita.ui.main.homepage.HomePageFragment
import com.zyuniversita.ui.main.navigation.NavigationFragment
import com.zyuniversita.ui.main.profile.ProfileFragment
import com.zyuniversita.ui.main.wordslist.language.LanguageListFragment
import com.zyuniversita.ui.main.wordslist.singleword.SingleWordFragment
import com.zyuniversita.ui.main.wordslist.words.WordsListFragment
import com.zyuniversita.ui.settings.SettingsActivity
import com.zyuniversita.ui.setup.local.LocalRegisterFragment
import com.zyuniversita.ui.setup.login.LoginFragment
import com.zyuniversita.ui.setup.registration.RegisterFragment

enum class Page {
    REGISTER,
    LOCAL_REGISTER,
    LOGIN,
    HOME,
    GAME,
    THEORY,
    THEORY_WORDS,
    SINGLE_WORD,
    FIND_CHARACTER,
    PROFILE,
    CHOOSE_GAME,
    CHOOSE_WORDS,
    NAV,
    //
    GAME_MULTIPLE_CHOOSING,
    GAME_MULTIPLE_CHOOSING_INV,
    GAME_WRITING,
    GAME_LINKING,
    SETTINGS;
}

object ApplicationFragmentFactory {

    private val pageMap: Map<Page, Class<out Fragment>> = mapOf(
        Page.REGISTER to RegisterFragment::class.java,
        Page.LOCAL_REGISTER to LocalRegisterFragment::class.java,
        Page.LOGIN to LoginFragment::class.java,
        Page.HOME to HomePageFragment::class.java,
        Page.GAME to GamesLanguageListFragment::class.java,
        Page.THEORY to LanguageListFragment::class.java,
        Page.THEORY_WORDS to WordsListFragment::class.java,
        Page.SINGLE_WORD to SingleWordFragment::class.java,
        Page.FIND_CHARACTER to FindCharacterFragment::class.java,
        Page.PROFILE to ProfileFragment::class.java,
        Page.CHOOSE_GAME to GameChoosingFragment::class.java,
        Page.CHOOSE_WORDS to WordsChoosingFragment::class.java,
        Page.NAV to NavigationFragment::class.java,
        Page.GAME_MULTIPLE_CHOOSING to MultipleChoiceGameFragment::class.java,
        Page.GAME_WRITING to WritingGameFragment::class.java
    )

    fun getPageClass(page: Page): Class<out Fragment> {
        return pageMap[page] ?: throw IllegalArgumentException("Page is not available.")
    }

    fun getPage(page: Page): Fragment {
        return pageMap[page]?.getDeclaredConstructor()?.newInstance()
            ?: throw IllegalArgumentException("Page is not available.")
    }
}

object ApplicationActivityFactory {
    private val pageMap: Map<Page, Class<out Activity>> = mapOf(
        Page.GAME_MULTIPLE_CHOOSING to GameActivity::class.java,
        Page.GAME_WRITING to GameActivity::class.java,
        Page.SETTINGS to SettingsActivity::class.java
    )

    fun getPageClass(page: Page): Class<out Activity> {
        return pageMap[page] ?: throw IllegalArgumentException("Page is not available.")
    }
}



