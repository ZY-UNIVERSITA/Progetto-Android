package com.zyuniversita.ui.games.main.uistate

sealed interface GameEvent {
    data object GameFinished: GameEvent
}