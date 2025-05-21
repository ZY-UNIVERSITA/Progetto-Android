package com.zyuniversita.data.local.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.zyuniversita.data.local.database.entities.LanguageEntity
import com.zyuniversita.data.local.database.entities.UserQuizPerformanceEntity

/**
 * Represents a user's quiz performance along with the associated language information.
 *
 * This data class combines the user's quiz performance data (embedded via [UserQuizPerformanceEntity])
 * with the related language details (fetched from [LanguageEntity]). The connection between these two components is 
 * established through a relation where the "languageCode" in the quiz performance matches the "code" in the language data.
 *
 * @property word The embedded quiz performance data for the user, represented as a [UserQuizPerformanceEntity].
 * @property userData The language information associated with the quiz performance, represented as a [LanguageEntity].
 */
data class UserQuizPerformanceWithLanguage(
    @Embedded
    val word: UserQuizPerformanceEntity,

    @Relation(
        parentColumn = "languageCode",
        entityColumn = "code"
    )
    val userData: LanguageEntity
)