package com.zyuniversita.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zyuniversita.domain.repository.PreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Name of the DataStore used to persist user settings and preferences.
private const val SETTINGS_PREFERENCES: String = "settings_preferences"

// Extension property on [Context] to provide access to the Preferences DataStore.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_PREFERENCES)

/**
 * Implementation of [PreferencesRepository] that uses Jetpack DataStore to persist and retrieve
 * user preferences such as username, user ID, database version, and app settings.
 *
 * This class provides reactive [Flow]-based access to values and ensures persistence across sessions.
 *
 * @param context Application context required to access the DataStore instance.
 */
class PreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesRepository {

    companion object {
        // Key to store/retrieve the user's username.
        private val USERNAME = stringPreferencesKey("username")

        // Key to store/retrieve the user's unique ID.
        private val USER_ID = longPreferencesKey("user_id")

        // Key to store/retrieve the database version.
        private val DATABASE_VERSION = intPreferencesKey("db_version")

        // Key to store/retrieve the word repetition preference.
        private val WORD_REPETITION = booleanPreferencesKey("word_repetition")

        private val APP_OPENED = booleanPreferencesKey("app_opened")
        private val LAST_OPENED_TIME = longPreferencesKey("last_opened_time")
    }

    /**
     * Flow that emits the stored username, or null if not set.
     */
    override val getUsername: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[USERNAME]
        }

    /**
     * Stores the provided [username] into the DataStore.
     *
     * @param username The username to persist.
     */
    override suspend fun setUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    /**
     * Flow that emits the stored user ID, or null if not set.
     */
    override val getUserId: Flow<Long?>
        get() = context.dataStore.data.map { preferences ->
            preferences[USER_ID]
        }

    /**
     * Stores the provided [userId] into the DataStore.
     *
     * @param userId The user ID to persist.
     */
    override suspend fun setUserId(userId: Long) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    /**
     * Flow that emits the stored database version. Defaults to 1 if not set.
     */
    override val getDatabaseVersion: Flow<Int>
        get() = context.dataStore.data.map { preferences ->
            preferences[DATABASE_VERSION] ?: 1
        }

    /**
     * Stores the given [databaseVersion] into the DataStore.
     *
     * @param databaseVersion The version number to save.
     */
    override suspend fun setDatabaseVersion(databaseVersion: Int) {
        context.dataStore.edit { preferences ->
            preferences[DATABASE_VERSION] = databaseVersion
        }
    }

    /**
     * Flow that emits whether word repetition is enabled. Defaults to `false` if not set.
     */
    override val getWordRepetition: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[WORD_REPETITION] ?: false
        }

    /**
     * Stores the given [repetition] preference into the DataStore.
     *
     * @param repetition `true` to enable word repetition, `false` to disable.
     */
    override suspend fun setWordRepetition(repetition: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[WORD_REPETITION] = repetition
        }
    }
}
