package com.zyuniversita.ui.games.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.model.userdata.GeneratedWordsStats
import com.zyuniversita.ui.games.main.uistate.GameEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {
    private val _gameEvent: Channel<GameEvent> = Channel<GameEvent>(Channel.BUFFERED)
    val gameEvent: Flow<GameEvent> = _gameEvent.receiveAsFlow()

    private val _wordsStats: MutableMap<Int, GeneratedWordsStats> = mutableMapOf()
    val wordsStats: Map<Int, GeneratedWordsStats> get() = _wordsStats.toMutableMap()

    fun updateWordsStats(wordsStats: Map<Int, GeneratedWordsStats>) {
        _wordsStats.clear()
        _wordsStats.putAll(wordsStats.toMutableMap())
    }

    fun showResult() {
        viewModelScope.launch {
            _gameEvent.send(GameEvent.ShowResults)
        }
    }

    fun finishGame() {
        viewModelScope.launch {
            _gameEvent.send(GameEvent.GameFinished)
        }
    }
}

