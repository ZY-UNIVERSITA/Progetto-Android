package com.zyuniversita.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.model.settings.SettingsToSave
import com.zyuniversita.domain.usecase.preferences.FetchUsernameUseCase
import com.zyuniversita.domain.usecase.preferences.FetchWordRepetitionUseCase
import com.zyuniversita.domain.usecase.preferences.SetUsernameUseCase
import com.zyuniversita.domain.usecase.preferences.SetWordRepetitionUseCase
import com.zyuniversita.ui.settings.uistate.SettingsEvent
import com.zyuniversita.ui.settings.uistate.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val fetchWordRepetitionUseCase: FetchWordRepetitionUseCase,
    private val fetchUsernameUseCase: FetchUsernameUseCase,
    private val setWordRepetitionUseCase: SetWordRepetitionUseCase,
    private val setUsernameUseCase: SetUsernameUseCase,
) : ViewModel() {
    private val _wordRepetition: MutableStateFlow<Boolean?> = MutableStateFlow<Boolean?>(null)
    val wordRepetition: StateFlow<Boolean?> = _wordRepetition.asStateFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    private val _settingsState: MutableStateFlow<SettingsState> = MutableStateFlow<SettingsState>(
        SettingsState()
    )

    private val _settingsEvent: Channel<SettingsEvent> =
        Channel<SettingsEvent>(Channel.BUFFERED)
    val settingsEvent: Flow<SettingsEvent> = _settingsEvent.receiveAsFlow()

    fun fetchAllData() {
        viewModelScope.launch {
            val repetition = fetchWordRepetitionUseCase().first()
            _wordRepetition.emit(repetition)
        }

        viewModelScope.launch {
            val username = fetchUsernameUseCase().first()
            _username.emit(username)
        }
    }

    fun saveData(newSettings: SettingsToSave) {
        viewModelScope.launch {
            setUsernameUseCase(newSettings.username)
            _settingsState.update {
                it.copy(
                    isUsernameSaving = false
                )
            }
        }

        viewModelScope.launch {
            setWordRepetitionUseCase(newSettings.repetition)
            _settingsState.update {
                it.copy(
                    isRepetitionSaving = false
                )
            }
        }

        viewModelScope.launch {
            _settingsState.collect { state ->
                if (state.isSavingComplete()) {
                    _settingsEvent.send(SettingsEvent.goToHome)
                }
            }
        }
    }
}