package com.zyuniversita.domain.usecase.userdata

import com.zyuniversita.domain.repository.UserDataRepository
import javax.inject.Inject

interface InsertNewUserUseCase {
    suspend operator fun invoke(username: String): Long
}

class InsertNewUserUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository) :
    InsertNewUserUseCase {
    override suspend fun invoke(username: String): Long {
        return userDataRepository.insertNewUser(username)
    }

}