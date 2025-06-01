package com.zyuniversita.data.remote.synchronization.model

import com.zyuniversita.domain.model.userdata.UserQuizPerformanceStats
import com.zyuniversita.domain.model.userdata.WordsUserData

data class DownloadedUserData(
    val userId: Long,
    val userData: List<UserQuizPerformanceStats>,
    val wordsUserData: List<WordsUserData>
)