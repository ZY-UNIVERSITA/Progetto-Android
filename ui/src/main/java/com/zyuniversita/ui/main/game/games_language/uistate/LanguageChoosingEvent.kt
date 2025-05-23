package com.zyuniversita.ui.main.game.games_language.uistate

sealed class LanguageChoosingEvent {
    data object GameChoosingPage : LanguageChoosingEvent()
}