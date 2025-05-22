package com.zyuniversita.ui.main.mainactivity

import androidx.lifecycle.ViewModel
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {
    // current fragment
    private val _currentFragment: MutableSharedFlow<Page> = MutableSharedFlow(0)
    val currentFragment: SharedFlow<Page> get() = _currentFragment.asSharedFlow()

    // current activity
    private val _currentActivity: MutableSharedFlow<Page> = MutableSharedFlow(0)
    val currentActivity: SharedFlow<Page> get() = _currentActivity.asSharedFlow()

    // current language
    private var _currentLanguage: String = "ch"
    val currentLanguage: String get() = _currentLanguage

    // change fragment
    suspend fun changeFragment(page: Page) {
        _currentFragment.emit(page)
    }

    // change activity
    suspend fun changeActivity(page: Page) {
        _currentActivity.emit(page)
    }

    // change language
    fun changeLanguage(lang: String) {
        _currentLanguage = lang
    }
}