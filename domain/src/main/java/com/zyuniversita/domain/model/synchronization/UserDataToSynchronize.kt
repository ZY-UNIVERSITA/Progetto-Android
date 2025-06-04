package com.zyuniversita.domain.model.synchronization

import com.zyuniversita.domain.model.userdata.UserQuizPerformanceStats
import com.zyuniversita.domain.model.userdata.WordsUserData

data class UserDataToSynchronize (
    val userId: Long,
    val userData: List<UserQuizPerformanceStats>,
    val wordsUserData: List<WordsUserData>
)

