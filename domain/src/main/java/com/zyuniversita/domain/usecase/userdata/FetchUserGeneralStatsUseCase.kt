package com.zyuniversita.domain.usecase.userdata

import com.zyuniversita.domain.model.userdata.GeneralUserStats
import com.zyuniversita.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

interface FetchUserGeneralStatsUseCase {
    suspend operator fun invoke(): SharedFlow<List<GeneralUserStats>>
}

class FetchUserGeneralStatsUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository) :
    FetchUserGeneralStatsUseCase {
    override suspend fun invoke(): SharedFlow<List<GeneralUserStats>> {
        return userDataRepository.generalUserStats
    }

}