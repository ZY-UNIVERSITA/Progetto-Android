package com.zyuniversita.data.repository

import com.zyuniversita.data.local.assets.ReadWordsFileDataSource
import com.zyuniversita.domain.repository.AssetsReaderRepository
import javax.inject.Inject

class AssetsReaderRepositoryImpl @Inject constructor(private val readWordsFileDataSource: ReadWordsFileDataSource) : AssetsReaderRepository {
    override suspend fun getInstructions(): String? {
        return readWordsFileDataSource.getInstructions()
    }

}