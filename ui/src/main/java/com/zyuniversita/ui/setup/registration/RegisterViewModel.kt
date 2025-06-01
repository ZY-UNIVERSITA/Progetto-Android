package com.zyuniversita.ui.setup.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.model.authentication.AuthenticationResponseEnum
import com.zyuniversita.domain.model.authentication.AuthenticationResponseInfo
import com.zyuniversita.domain.model.authentication.RegistrationInfo
import com.zyuniversita.domain.usecase.authentication.RegisterUseCase
import com.zyuniversita.ui.setup.uistate.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    ViewModel() {
    private var _responseInfo: AuthenticationResponseInfo? = null
    val responseInfo: AuthenticationResponseInfo? get() = _responseInfo

    private val _event = Channel<AuthEvent>(Channel.BUFFERED)
    val event: Flow<AuthEvent> = _event.receiveAsFlow()

    fun register(registrationInfo: RegistrationInfo) {
        viewModelScope.launch {
            val response = registerUseCase(registrationInfo)


            val event = when(response.serverResponse) {
                AuthenticationResponseEnum.SUCCESS -> {
                    _responseInfo = response
                    AuthEvent.AuthenticationSuccessful
                }
                AuthenticationResponseEnum.IDENTIFICATION_FAILURE -> AuthEvent.IdentificationError
                AuthenticationResponseEnum.CONNECTION_ERROR -> AuthEvent.ConnectionError
            }

            _event.send(event)
        }
    }

    fun goToLogin() {
        viewModelScope.launch {
            _event.send(AuthEvent.LoginAuth)
        }
    }
}