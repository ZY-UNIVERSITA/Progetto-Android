package com.zyuniversita.ui.main.wordslist.words.uistate

sealed class WordsListEvent {
    data object goToWord: WordsListEvent()
}