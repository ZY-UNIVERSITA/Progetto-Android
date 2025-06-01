package com.zyuniversita.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.model.userdata.GeneralUserStats
import com.zyuniversita.domain.usecase.preferences.FetchUserIdUseCase
import com.zyuniversita.domain.usecase.preferences.FetchUsernameUseCase
import com.zyuniversita.domain.usecase.synchronization.DownloadUserDataUseCase
import com.zyuniversita.domain.usecase.userdata.FetchUserGeneralStatsUseCase
import com.zyuniversita.domain.usecase.userdata.InsertAllUserDataEntriesUseCase
import com.zyuniversita.domain.usecase.userdata.StartFetchUserGeneralStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchUsernameUseCase: FetchUsernameUseCase,
    private val fetchUserIdUseCase: FetchUserIdUseCase,
    private val startFetchUserGeneralStatsUseCase: StartFetchUserGeneralStatsUseCase,
    private val fetchUserGeneralStatsUseCase: FetchUserGeneralStatsUseCase,

    private val downloadUserDataUseCase: DownloadUserDataUseCase,
    private val insertAllUserDataEntriesUseCase: InsertAllUserDataEntriesUseCase
) :
    ViewModel() {
    private val _userId: MutableStateFlow<Long?> = MutableStateFlow<Long?>(null)
    private val userId: StateFlow<Long?> = _userId.asStateFlow()

    private val _username: MutableStateFlow<String?> = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username.asStateFlow()

    private val _userData: MutableStateFlow<List<GeneralUserStats>> =
        MutableStateFlow<List<GeneralUserStats>>(
            listOf()
        )
    val userData: StateFlow<List<GeneralUserStats>> = _userData.asStateFlow()

    fun fetchUserId() {
        viewModelScope.launch {
            fetchUserIdUseCase().first().let { userId ->
                _userId.emit(userId)
            }

//            val response = downloadUserDataUseCase(2)
//
//            if (response.response == SynchronizationResponseEnum.SUCCESS) {
//                insertAllUserDataEntriesUseCase(2, response.data.userData)
//            }
        }
    }

    fun fetchUserData() {
        viewModelScope.launch {
            userId.filterNotNull().first().let { userId ->
                startFetchUserGeneralStatsUseCase(userId)
                val userData = fetchUserGeneralStatsUseCase().first()
                _userData.emit(userData)
            }
        }
    }

    fun fetchUsername() {
        viewModelScope.launch {
            val username = fetchUsernameUseCase().first()
            _username.emit(username)
        }
    }
}
