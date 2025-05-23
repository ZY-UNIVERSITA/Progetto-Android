package com.zyuniversita.ui.main.game.games_language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.model.AvailableLanguage
import com.zyuniversita.domain.usecase.FetchLanguageUseCase
import com.zyuniversita.domain.usecase.words.StartFetchWordsAndUserDataByLanguageUseCase
import com.zyuniversita.ui.main.game.games_language.uistate.LanguageChoosingEvent
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesLanguageListViewModel @Inject constructor(
    private val fetchLanguageUseCase: FetchLanguageUseCase,
    private val startFetchWordsAndUserDataByLanguageUseCase: StartFetchWordsAndUserDataByLanguageUseCase,
) : ViewModel() {
    // Manage available language list
    private val _availableLanguage: MutableStateFlow<List<AvailableLanguage>> =
        MutableStateFlow(listOf())
    val availableLanguage: StateFlow<List<AvailableLanguage>> = _availableLanguage.asStateFlow()

    private val _uiEvent: MutableSharedFlow<LanguageChoosingEvent> = MutableSharedFlow(0)
    val uiEvent: SharedFlow<LanguageChoosingEvent> get() = _uiEvent.asSharedFlow()

    lateinit var nextFragment: Page
    lateinit var language: String

    fun fetchData() {
        viewModelScope.launch {
            val languageList: List<AvailableLanguage> = fetchLanguageUseCase().first()

            _availableLanguage.emit(languageList)
        }
    }

    fun selectLanguage(nextFragment: Page, language: String) {
        this.nextFragment = nextFragment
        this.language = language

        viewModelScope.launch {
            startFetchWordsAndUserDataByLanguageUseCase(language)
            _uiEvent.emit(LanguageChoosingEvent.GameChoosingPage)
        }
    }
}