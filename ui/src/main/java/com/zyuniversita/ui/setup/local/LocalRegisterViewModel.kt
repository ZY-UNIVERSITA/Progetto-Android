package com.zyuniversita.ui.setup.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.model.authentication.AuthenticationResponseInfo
import com.zyuniversita.ui.setup.uistate.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalRegisterViewModel @Inject constructor() : ViewModel() {
    private var _responseInfo: AuthenticationResponseInfo? = null
    val responseInfo: AuthenticationResponseInfo? get() = _responseInfo

    private val _event = Channel<AuthEvent>(Channel.BUFFERED)
    val event: Flow<AuthEvent> = _event.receiveAsFlow()

    fun goToLogin() {
        viewModelScope.launch {
            _event.send(AuthEvent.LoginAuth)
        }
    }
}