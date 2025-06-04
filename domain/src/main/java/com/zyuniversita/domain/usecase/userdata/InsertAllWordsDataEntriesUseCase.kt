package com.zyuniversita.domain.usecase.userdata

import com.zyuniversita.domain.model.userdata.WordsUserData
import com.zyuniversita.domain.repository.UserDataRepository
import javax.inject.Inject

interface InsertAllWordsDataEntriesUseCase {
    suspend operator fun invoke(userId: Long, wordsData: List<WordsUserData>)
}

class InsertAllWordsDataEntriesUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository): InsertAllWordsDataEntriesUseCase {
    override suspend fun invoke(userId: Long, wordsData: List<WordsUserData>) {
        userDataRepository.insertAllWordsDataByUserID(userId, wordsData)
    }

}