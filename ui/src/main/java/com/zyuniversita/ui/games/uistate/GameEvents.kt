package com.zyuniversita.ui.games.uistate

sealed interface GameEvents {
    data object DataLoaded: GameEvents
    data object NewWord: GameEvents
    data object GameEnded: GameEvents
}
