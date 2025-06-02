package com.zyuniversita.domain.usecase.assetsreader

import com.zyuniversita.domain.repository.AssetsReaderRepository
import javax.inject.Inject

interface ReadWordsFileUseCase {
    suspend operator fun invoke(): String?
}

class ReadWordsFileUseCaseImpl @Inject constructor(private val assetsReaderRepository: AssetsReaderRepository): ReadWordsFileUseCase {
    override suspend fun invoke(): String? {
        return assetsReaderRepository.getInstructions()
    }

}