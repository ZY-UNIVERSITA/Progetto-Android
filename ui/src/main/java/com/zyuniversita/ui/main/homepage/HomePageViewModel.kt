package com.zyuniversita.ui.main.homepage

import androidx.lifecycle.ViewModel
import com.zyuniversita.domain.usecase.assetsreader.ReadWordsFileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val readWordsFileUseCase: ReadWordsFileUseCase) :
    ViewModel() {

    suspend fun readWords(): String {
        val words = readWordsFileUseCase()

        return words ?: ""
    }
}