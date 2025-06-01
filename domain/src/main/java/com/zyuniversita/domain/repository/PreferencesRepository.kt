package com.zyuniversita.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val getUsername: Flow<String?>
    suspend fun setUsername(username: String): Unit

    val getUserId: Flow<Long?>
    suspend fun setUserId(userId: Long): Unit

    val getDatabaseVersion: Flow<Int>
    suspend fun setDatabaseVersion(databaseVersion: Int): Unit

    val getWordRepetition: Flow<Boolean>
    suspend fun setWordRepetition(repetition: Boolean): Unit

    val hasAppBeenOpened: Flow<Boolean>
    suspend fun saveAppOpened(): Unit

    val hasSynchronization: Flow<Boolean>
    suspend fun changeSynchronization(status: Boolean): Unit
}