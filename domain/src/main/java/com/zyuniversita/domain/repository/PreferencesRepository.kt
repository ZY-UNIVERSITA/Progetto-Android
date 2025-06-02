package com.zyuniversita.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val getUsername: Flow<String?>
    suspend fun setUsername(username: String): Unit
    suspend fun removeUsername(): Unit

    val getUserId: Flow<Long?>
    suspend fun setUserId(userId: Long): Unit
    suspend fun removeUserId(): Unit

    val getDatabaseVersion: Flow<Int>
    suspend fun setDatabaseVersion(databaseVersion: Int): Unit

    val getWordRepetition: Flow<Boolean>
    suspend fun setWordRepetition(repetition: Boolean): Unit
    suspend fun removeWordRepetition(): Unit

    val hasAppBeenOpened: Flow<Boolean>
    suspend fun saveAppOpened(): Unit
    suspend fun removeHasAppBeenOpened(): Unit

    val hasSynchronization: Flow<Boolean>
    suspend fun changeSynchronization(status: Boolean): Unit
    suspend fun removeHasSynchronization(): Unit

    val getEmail: Flow<String?>
    suspend fun setEmail(email: String): Unit
    suspend fun removeEmail(): Unit

    suspend fun resetPreferences(): Unit
}
