package com.zyuniversita.domain.usecase.userdata

import com.zyuniversita.domain.model.userdata.GeneratedWordsStats
import com.zyuniversita.domain.repository.UserDataRepository
import javax.inject.Inject

interface UpdateUserDataUseCase: suspend (Map<Int, GeneratedWordsStats>) -> Unit

class UpdateUserDataUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository): UpdateUserDataUseCase {
    override suspend fun invoke(wordsStats: Map<Int, GeneratedWordsStats>) {
        userDataRepository.updateUserData(wordsStats)
    }

}