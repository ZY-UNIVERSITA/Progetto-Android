package com.zyuniversita.ui.main.game.words_choosing.uistate

sealed interface WordChoosingEvent {
    data object NavigateToGamePage: WordChoosingEvent
}