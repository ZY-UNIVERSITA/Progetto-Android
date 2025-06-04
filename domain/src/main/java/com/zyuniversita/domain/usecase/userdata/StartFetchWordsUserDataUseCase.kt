package com.zyuniversita.domain.usecase.userdata

import com.zyuniversita.domain.repository.UserDataRepository
import javax.inject.Inject

interface StartFetchWordsUserDataUseCase : suspend () -> Unit

class StartFetchWordsUserDataUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository) : StartFetchWordsUserDataUseCase {
    override suspend fun invoke() {
        userDataRepository.getWordsUserData()
    }
}