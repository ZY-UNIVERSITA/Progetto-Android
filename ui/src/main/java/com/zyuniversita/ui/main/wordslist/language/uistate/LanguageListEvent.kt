package com.zyuniversita.ui.main.wordslist.language.uistate

sealed class LanguageListEvent {
    data object goToWordsList: LanguageListEvent()
}