package com.zyuniversita.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zyuniversita.data.local.database.entities.UserInfoEntity

/**
 * Data Access Object (DAO) for managing user information data.
 *
 * This interface provides methods for inserting user information as well as retrieving
 * user information from the database by their unique identifier.
 */
@Dao
interface UserInfoDao {

    /**
     * Inserts a list of [UserInfoEntity] objects into the database.
     *
     * If a conflict occurs (e.g., a user with the same primary key already exists), the existing entry will be replaced.
     *
     * @param list A list of [UserInfoEntity] objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UserInfoEntity>)

    /**
     * Inserts a single [UserInfoEntity] object into the database.
     *
     * If a conflict occurs, the existing user information will be replaced with the new one.
     *
     * @param user The [UserInfoEntity] object to be inserted.
     * @return The row ID of the newly inserted user.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserInfoEntity): Long

    /**
     * Retrieves a user from the database by their user ID.
     *
     * @param userId The unique identifier of the user to be retrieved.
     * @return A [UserInfoEntity] object representing the user with the specified ID.
     */
    @Query(
        """
            SELECT * 
            FROM userInfo
            WHERE userId = :userId
        """
    )
    suspend fun getUserByUserId(userId: Int): UserInfoEntity
}