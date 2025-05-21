package com.zyuniversita.data.local.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.zyuniversita.data.local.database.entities.WordEntity
import com.zyuniversita.data.local.database.entities.WordUserDataEntity

/**
 * Represents a word along with its associated user-specific data.
 *
 * This data class combines the word details (embedded via [WordEntity]) with the corresponding user data
 * (retrieved from [WordUserDataEntity]). The relation is established when the `wordId` field in [WordEntity] 
 * matches the `wordId` field in [WordUserDataEntity]. The user data is optional and may be null if no associated
 * record exists.
 *
 * @property word The detailed information about the word, represented by a [WordEntity].
 * @property userData The user-specific data for the word, represented by a [WordUserDataEntity]. It can be null if no data is available.
 */
data class WordWithUserData(
    @Embedded val word: WordEntity,

    @Relation(
        parentColumn = "wordId",
        entityColumn = "wordId"
    )
    val userData: WordUserDataEntity?
)