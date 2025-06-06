package com.zyuniversita.ui.settings.uistate

data class SettingsState(
    val isUsernameSaving: Boolean = true,
    val isRepetitionSaving: Boolean = true,
    val isSynchronizationSaving: Boolean = true,
) {
    fun isSavingComplete(): Boolean = !isUsernameSaving && !isRepetitionSaving && !isSynchronizationSaving
}
