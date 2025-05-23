package com.zyuniversita.ui.main.game.games_choosing.uistate

import com.zyuniversita.ui.main.mainactivity.mainenum.Page

sealed class GameChoosingEvent {
    class GamePage(val nextFragment: Page): GameChoosingEvent()
}