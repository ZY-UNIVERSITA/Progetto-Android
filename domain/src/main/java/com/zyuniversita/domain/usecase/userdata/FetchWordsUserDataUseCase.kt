package com.zyuniversita.domain.usecase.userdata

import com.zyuniversita.domain.model.userdata.WordsUserData
import com.zyuniversita.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface FetchWordsUserDataUseCase {
    suspend operator fun invoke(): StateFlow<List<WordsUserData>>
}

class FetchWordsUserDataUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository): FetchWordsUserDataUseCase {
    override suspend fun invoke(): StateFlow<List<WordsUserData>> {
        return userDataRepository.wordsUserData
    }
}