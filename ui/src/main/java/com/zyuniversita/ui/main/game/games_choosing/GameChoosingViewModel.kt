package com.zyuniversita.ui.main.game.games_choosing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.usecase.words.StartFetchWordsForQuizUseCase
import com.zyuniversita.ui.main.game.games_choosing.uistate.GameChoosingEvent
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameChoosingViewModel @Inject constructor(
    private val startFetchWordsForQuizUseCase: StartFetchWordsForQuizUseCase
) :
    ViewModel() {
    private val _uiState: MutableSharedFlow<GameChoosingEvent> =
        MutableSharedFlow<GameChoosingEvent>(0)
    val uiState: SharedFlow<GameChoosingEvent> get() = _uiState.asSharedFlow()

    fun loadNextData(language: String, nextFragment: Page) {
        viewModelScope.launch {
            startFetchWordsForQuizUseCase(language)
            _uiState.emit(GameChoosingEvent.GamePage(nextFragment))
        }
    }
}