package com.zyuniversita.ui.main.wordslist.singleword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.model.words.WordProgress
import com.zyuniversita.domain.usecase.singleword.FetchSingleWordProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleWordViewModel @Inject constructor(private val fetchSingleWordProgressUseCase: FetchSingleWordProgressUseCase) :
    ViewModel() {

    private val _singleWord: MutableSharedFlow<WordProgress> = MutableSharedFlow(1)
    val singleWord: SharedFlow<WordProgress> = _singleWord.asSharedFlow()

    fun fetchData() {
        viewModelScope.launch {
            val word = fetchSingleWordProgressUseCase().first()
            _singleWord.emit(word)
        }
    }

}