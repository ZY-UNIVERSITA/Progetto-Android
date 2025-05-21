package com.zyuniversita.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zyuniversita.data.local.database.entities.LanguageEntity

/**
 * Data Access Object (DAO) for handling operations related to language data.
 *
 * This interface provides methods for inserting a list of language entities and retrieving all available languages,
 * ordered by their name.
 */
@Dao
interface LanguageDao {

    /**
     * Inserts a list of [LanguageEntity] objects into the database.
     *
     * If a conflict occurs (e.g., a language with the same primary key already exists), the existing entry will be replaced.
     *
     * @param list A list of [LanguageEntity] objects to be inserted into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<LanguageEntity>)

    /**
     * Retrieves all available languages from the database, ordered alphabetically by their name.
     *
     * @return A list of [LanguageEntity] objects representing the available languages.
     */
    @Query("SELECT * FROM language ORDER BY name")
    suspend fun getAllLanguage(): List<LanguageEntity>
}