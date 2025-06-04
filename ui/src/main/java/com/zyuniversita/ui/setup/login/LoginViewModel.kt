package com.zyuniversita.ui.setup.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.model.authentication.AuthenticationResponseEnum
import com.zyuniversita.domain.model.authentication.AuthenticationResponseInfo
import com.zyuniversita.domain.model.authentication.LoginInfo
import com.zyuniversita.domain.model.synchronization.SynchronizationResponseEnum
import com.zyuniversita.domain.model.synchronization.UserDataToSynchronize
import com.zyuniversita.domain.usecase.authentication.LoginUseCase
import com.zyuniversita.domain.usecase.synchronization.DownloadUserDataUseCase
import com.zyuniversita.domain.usecase.userdata.DeleteAllUserDataEntriesUseCase
import com.zyuniversita.domain.usecase.userdata.DeleteAllWordsDataEntriesUseCase
import com.zyuniversita.domain.usecase.userdata.InsertAllUserDataEntriesUseCase
import com.zyuniversita.domain.usecase.userdata.InsertAllWordsDataEntriesUseCase
import com.zyuniversita.domain.usecase.userdata.InsertNewUserUseCase
import com.zyuniversita.ui.setup.uistate.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val downloadUserDataUseCase: DownloadUserDataUseCase,

    private val deleteAllUserDataEntriesUseCase: DeleteAllUserDataEntriesUseCase,
    private val deleteAllWordsDataEntriesUseCase: DeleteAllWordsDataEntriesUseCase,

    private val insertAllWordsDataEntriesUseCase: InsertAllWordsDataEntriesUseCase,
    private val insertAllUserDataEntriesUseCase: InsertAllUserDataEntriesUseCase,

    private val insertNewUserUseCase: InsertNewUserUseCase,
) : ViewModel() {
    private var _responseInfo: AuthenticationResponseInfo? = null
    val responseInfo: AuthenticationResponseInfo? get() = _responseInfo

    private val _event = Channel<AuthEvent>(Channel.BUFFERED)
    val event: Flow<AuthEvent> = _event.receiveAsFlow()

    fun login(loginInfo: LoginInfo) {
        viewModelScope.launch {
            val response = loginUseCase(loginInfo)

            val event = when (response.serverResponse) {
                AuthenticationResponseEnum.SUCCESS -> {
                    _responseInfo = response
                    insertUser(authenticationResponseInfo = response)

                    val data = downloadUserData(response.userId)
                    updateUserData(response.userId, data)

                    AuthEvent.AuthenticationSuccessful
                }

                AuthenticationResponseEnum.IDENTIFICATION_FAILURE -> AuthEvent.IdentificationError
                AuthenticationResponseEnum.CONNECTION_ERROR -> AuthEvent.ConnectionError
            }

            println("io ho terminato")
            _event.send(event)
        }
    }

    fun goToRegistrationPage() {
        viewModelScope.launch {
            _event.send(AuthEvent.RegistrationAuth)
        }
    }

    fun goToLocalRegistrationPage() {
        viewModelScope.launch {
            _event.send(AuthEvent.LocalRegistrationAuth)
        }
    }

    private suspend fun insertUser(authenticationResponseInfo: AuthenticationResponseInfo) {
        insertNewUserUseCase(authenticationResponseInfo.userId, authenticationResponseInfo.username)
    }

    private suspend fun downloadUserData(userId: Long): UserDataToSynchronize {
        val response = downloadUserDataUseCase(userId)

        val event = when (response.response) {
            SynchronizationResponseEnum.SUCCESS -> {
                AuthEvent.AuthenticationSuccessful
            }

            SynchronizationResponseEnum.CONNECTION_ERROR -> AuthEvent.ConnectionError
            SynchronizationResponseEnum.ERROR -> AuthEvent.IdentificationError
        }

        _event.send(event)

        return response.data
    }

    private suspend fun updateUserData(userId: Long, userData: UserDataToSynchronize) {
        coroutineScope {
            val wordsDataDeferred = async {
                deleteAllWordsDataEntriesUseCase(userId)
                insertAllWordsDataEntriesUseCase(userId, userData.wordsUserData)
            }

            val userDataDeferred = async {
                deleteAllUserDataEntriesUseCase(userId)
                insertAllUserDataEntriesUseCase(userId, userData.userData)
            }

            awaitAll(wordsDataDeferred, userDataDeferred)

            val event = AuthEvent.DownloadSuccessful
            _event.send(event)
        }

    }
}