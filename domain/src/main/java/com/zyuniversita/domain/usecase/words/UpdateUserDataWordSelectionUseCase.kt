package com.zyuniversita.domain.usecase.words

import com.zyuniversita.domain.repository.UserDataRepository
import javax.inject.Inject

interface UpdateUserDataWordSelectionUseCase: suspend (Map<Int, Boolean>) -> Unit

class UpdateUserDataWordSelectionUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository): UpdateUserDataWordSelectionUseCase {
    override suspend fun invoke(selectionMap: Map<Int, Boolean>) {
        userDataRepository.updateSelectedWord(selectionMap)
    }
}