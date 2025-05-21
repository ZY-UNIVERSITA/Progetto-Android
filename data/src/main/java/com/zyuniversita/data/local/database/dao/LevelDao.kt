package com.zyuniversita.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zyuniversita.data.local.database.entities.LevelEntity

/**
 * Data Access Object (DAO) for handling operations related to levels.
 *
 * This interface provides methods to insert levels for various languages and to query levels based on a specific language.
 */
@Dao
interface LevelDao {

    /**
     * Inserts a list of [LevelEntity] objects into the database.
     *
     * If a conflict occurs (for example, an entry with the same primary key already exists), the existing entry 
     * will be replaced with the new one.
     *
     * @param list A list of [LevelEntity] objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<LevelEntity>)

    /**
     * Retrieves all levels for the specified language from the database.
     *
     * The levels are ordered by their code in ascending order.
     *
     * @param lang The language code used to filter the levels.
     * @return A list of [LevelEntity] objects associated with the specified language.
     */
    @Query(
        """
            SELECT * FROM level
            WHERE languageCode = :lang
            ORDER BY code
        """
    )
    suspend fun levelsForLanguage(lang: String): List<LevelEntity>
}