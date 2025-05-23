package com.zyuniversita.ui.main.wordslist.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.model.AvailableLanguage
import com.zyuniversita.domain.usecase.FetchLanguageUseCase
import com.zyuniversita.domain.usecase.wordslist.StartFetchWordsByLanguageGeneralListUseCase
import com.zyuniversita.ui.main.wordslist.language.uistate.LanguageListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageListViewModel @Inject constructor(
    private val fetchLanguageUseCase: FetchLanguageUseCase,
    private val startFetchWordsByLanguageGeneralListUseCase: StartFetchWordsByLanguageGeneralListUseCase
) :
    ViewModel() {
    private val _languageList: MutableSharedFlow<List<AvailableLanguage>> =
        MutableSharedFlow<List<AvailableLanguage>>(1)
    val languageList: SharedFlow<List<AvailableLanguage>> = _languageList.asSharedFlow()

    private val _uiState: MutableSharedFlow<LanguageListEvent> = MutableSharedFlow(0)
    val uiState: SharedFlow<LanguageListEvent> = _uiState.asSharedFlow()

    fun fetchData() {
        viewModelScope.launch {
            val languageList: List<AvailableLanguage> = fetchLanguageUseCase().first()
            _languageList.emit(languageList)
        }
    }

    fun selectLanguage(lang: String) {
        viewModelScope.launch {
            startFetchWordsByLanguageGeneralListUseCase(lang)
            _uiState.emit(LanguageListEvent.goToWordsList)
        }
    }
}