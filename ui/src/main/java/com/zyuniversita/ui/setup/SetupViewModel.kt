package com.zyuniversita.ui.setup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.usecase.languages.StartFetchLanguageUseCase
import com.zyuniversita.domain.usecase.preferences.FetchCurrentDatabaseVersionUseCase
import com.zyuniversita.domain.usecase.preferences.FetchUsernameUseCase
import com.zyuniversita.domain.usecase.preferences.SetCurrentDatabaseVersionUseCase
import com.zyuniversita.domain.usecase.preferences.SetUserIdUseCase
import com.zyuniversita.domain.usecase.preferences.SetUsernameUseCase
import com.zyuniversita.domain.usecase.userdata.InsertNewUserUseCase
import com.zyuniversita.domain.usecase.worddatabase.FetchLatestDatabaseVersionUseCase
import com.zyuniversita.domain.usecase.worddatabase.UpdateWordDatabaseUseCase
import com.zyuniversita.domain.usecase.worker.RemoveNotificationWorkerUseCase
import com.zyuniversita.domain.usecase.worker.StartNotificationWorkerUseCase
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import com.zyuniversita.ui.setup.uistate.SetupEvent
import com.zyuniversita.ui.setup.uistate.SetupUiState
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
class SetupViewModel @Inject constructor(
    private val fetchUsernameUseCase: FetchUsernameUseCase,
    private val setUsernameUseCase: SetUsernameUseCase,
    private val insertNewUserUseCase: InsertNewUserUseCase,
    private val setUserIdUseCase: SetUserIdUseCase,

    private val fetchCurrentDatabaseVersionUseCase: FetchCurrentDatabaseVersionUseCase,
    private val setCurrentDatabaseVersionUseCase: SetCurrentDatabaseVersionUseCase,

    private val fetchLatestDatabaseVersionUseCase: FetchLatestDatabaseVersionUseCase,
    private val updateWordDatabaseUseCase: UpdateWordDatabaseUseCase,

    private val startFetchLanguageUseCase: StartFetchLanguageUseCase,

    private val startNotificationWorkerUseCase: StartNotificationWorkerUseCase,
    private val removeNotificationWorkerUseCase: RemoveNotificationWorkerUseCase,
) : ViewModel() {

    /* ---------------- UI State ---------------- */
    private val _uiState: MutableStateFlow<SetupUiState> = MutableStateFlow(SetupUiState())
    val uiState: StateFlow<SetupUiState> = _uiState.asStateFlow()

    /* ---------------- Events ---------------- */
    private val _event = Channel<SetupEvent>(Channel.BUFFERED)
    val event: Flow<SetupEvent> = _event.receiveAsFlow()

    /* Navigation */
    fun navigation() {
        viewModelScope.launch {
            uiState.collect { state ->
                if (state.isSetupCompleted) {
                    _event.send(SetupEvent.NavigateToHome)
                    _uiState.emit(SetupUiState())
                }
            }
        }
    }

    /* ---------------- Username and UserID ---------------- */
    private fun updateUsername(name: String?) {
        // update the old value of the UI
        // data class is immutable so it needed a copy of the old object with new values
        _uiState.update {
            it.copy(
                username = name,
                isUsernameMissing = name.isNullOrBlank()
            )
        }
    }

    private fun updateUserId() {
        _uiState.update {
            it.copy(isUserIdMissing = false)
        }
    }

    fun fetchUsername() {
        viewModelScope.launch {
            val name = fetchUsernameUseCase().first()

            updateUsername(name)

            name?.let {
                updateUserId()
            } ?: _event.send(SetupEvent.NavigateToLogin)
        }
    }

    fun onUsernameConfirmed(userId: Long?, username: String) {
        viewModelScope.launch {
            // Update the id
            if (userId == null) {
                val userIdDB: Long = insertNewUserUseCase(userId, username)
                setUserIdUseCase(userIdDB)
            } else {
                setUserIdUseCase(userId)
            }

            updateUserId()
        }

        viewModelScope.launch {
            // update the username
            setUsernameUseCase(username)
            updateUsername(username)
        }
    }

    fun changePage(page: Page) {
        viewModelScope.launch {
            when (page) {
                Page.LOGIN -> _event.send(SetupEvent.NavigateToLogin)
                Page.REGISTER -> _event.send(SetupEvent.NavigateToRegister)
                Page.LOCAL_REGISTER -> _event.send(SetupEvent.NavigateToLocalRegister)
                else -> throw IllegalArgumentException("Page not supported")
            }
        }
    }

    /* ---------------- Database ---------------- */
    fun checkDatabase() {
        viewModelScope.launch {
            val current = fetchCurrentDatabaseVersionUseCase().first()

            val latest = runCatching { fetchLatestDatabaseVersionUseCase() }
                .getOrElse {
                    Log.e("Setup Check Database", "Problem with fetching latest DB version", it)
                    null
                }

            // if the fetching is successful and there is a new version then update the db
            if (latest != null && current < latest) {
                updateWordDatabaseUseCase(latest)
                setCurrentDatabaseVersionUseCase(latest)
            }

            _uiState.update {
                it.copy(isDatabaseUpdating = false)
            }
        }
    }

    /* Language loading */
    fun loadLanguage() {
        viewModelScope.launch {
            startFetchLanguageUseCase()
            _uiState.update {
                it.copy(isLanguageLoading = false)
            }
        }
    }

    /* Restart Notification Worker */
    fun startNotificationWorker() {
        removeNotificationWorker()
        startNotificationWorkerUseCase()
    }

    private fun removeNotificationWorker() {
        removeNotificationWorkerUseCase()
    }
}