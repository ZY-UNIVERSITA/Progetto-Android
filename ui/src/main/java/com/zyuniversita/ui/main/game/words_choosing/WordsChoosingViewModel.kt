package com.zyuniversita.ui.main.game.words_choosing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.model.WordProgress
import com.zyuniversita.domain.usecase.mapping.GroupWordsAndUserDataByLevelUseCase
import com.zyuniversita.domain.usecase.words.FetchWordsAndUserDataByLanguageUseCase
import com.zyuniversita.domain.usecase.words.UpdateUserDataWordSelectionUseCase
import com.zyuniversita.ui.main.game.words_choosing.uistate.WordChoosingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsChoosingViewModel @Inject constructor(
    private val fetchWordsAndUserDataByLanguageUseCase: FetchWordsAndUserDataByLanguageUseCase,

    private val updateUserDataWordSelectionUseCase: UpdateUserDataWordSelectionUseCase,

    private val groupWordsAndUserDataByLevelUseCase: GroupWordsAndUserDataByLevelUseCase
) : ViewModel() {
    private val _wordsList: MutableStateFlow<MutableList<MutableList<WordProgress>>> =
        MutableStateFlow(mutableListOf())
    val wordsList: StateFlow<MutableList<MutableList<WordProgress>>> get() = _wordsList.asStateFlow()

    private val _modifiedList: MutableMap<Int, Boolean> = mutableMapOf()
    private val modifiedList: Map<Int, Boolean> get() = _modifiedList

    private val _uiEvent: Channel<WordChoosingEvent> = Channel<WordChoosingEvent>(Channel.BUFFERED)
    val uiEvent: Flow<WordChoosingEvent> get() = _uiEvent.receiveAsFlow()

    fun fetchData() {
        viewModelScope.launch {
            val wordsList = fetchWordsAndUserDataByLanguageUseCase().first()
            _wordsList.emit(groupWordsAndUserDataByLevelUseCase(wordsList))
        }
    }

    fun changeWordSelection(wordId: Int, selection: Boolean) {
        _modifiedList[wordId] = selection
    }

    fun updateUserDataWordSelection() {
        viewModelScope.launch {
            updateUserDataWordSelectionUseCase(modifiedList)
            _uiEvent.send(WordChoosingEvent.NavigateToGamePage)
        }
    }
}