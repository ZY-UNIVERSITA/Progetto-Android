package com.zyuniversita.domain.repository

interface AssetsReaderRepository {
    suspend fun getInstructions(): String?
}