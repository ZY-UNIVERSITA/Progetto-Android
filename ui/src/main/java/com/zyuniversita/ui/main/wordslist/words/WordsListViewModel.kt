package com.zyuniversita.ui.main.wordslist.words

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.model.words.Word
import com.zyuniversita.domain.usecase.mapping.GroupWordsByLevelUseCase
import com.zyuniversita.domain.usecase.singleword.StartFetchSingleWordProgressUseCase
import com.zyuniversita.domain.usecase.wordslist.FetchWordsByLanguageGeneralListUseCase
import com.zyuniversita.ui.main.wordslist.words.uistate.WordsListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsListViewModel @Inject constructor(
    private val fetchWordsByLanguageGeneralListUseCase: FetchWordsByLanguageGeneralListUseCase,
    private val groupWordsByLevelUseCase: GroupWordsByLevelUseCase,
    private val startFetchSingleWordProgressUseCase: StartFetchSingleWordProgressUseCase
) :
    ViewModel() {
    private val _originalWordsList: MutableList<Word> = mutableListOf()

    private val _wordsList: MutableSharedFlow<MutableList<MutableList<Word>>> = MutableSharedFlow(1)
    val wordsList: SharedFlow<MutableList<MutableList<Word>>> = _wordsList.asSharedFlow()

    private val _uiEvent: MutableSharedFlow<WordsListEvent> = MutableSharedFlow(0)
    val uiEvent: SharedFlow<WordsListEvent> = _uiEvent.asSharedFlow()

    fun fetchData() {
        viewModelScope.launch {
            val wordsList = fetchWordsByLanguageGeneralListUseCase().first()

            _originalWordsList.apply {
                clear()
                addAll(wordsList.toMutableList())
            }

            updateData(_originalWordsList)
        }
    }

    private suspend fun updateData(list: MutableList<Word>) {
        _wordsList.emit(groupWordsByLevelUseCase(list))
    }

    fun filterData(filter: String) {
        viewModelScope.launch {
            val filteredList = _originalWordsList.filter { it.word.contains(filter) }.toMutableList()
            updateData(filteredList)
        }
    }

    fun removeFilter() {
        viewModelScope.launch {
            updateData(_originalWordsList)
        }
    }

    fun fetchSingleWord(wordId: Int): Unit {
        viewModelScope.launch {
            startFetchSingleWordProgressUseCase(wordId = wordId)
            _uiEvent.emit(WordsListEvent.goToWord)
        }
    }
}