package com.zyuniversita.domain.repository

import com.zyuniversita.domain.model.userdata.GeneralUserStats
import com.zyuniversita.domain.model.userdata.GeneratedWordsStats
import kotlinx.coroutines.flow.SharedFlow

interface UserDataRepository {
    suspend fun updateSelectedWord(wordsList: Map<Int, Boolean>)
    suspend fun updateUserData(wordlist: Map<Int, GeneratedWordsStats>)
    suspend fun insertNewUser(username: String): Long
    suspend fun updateNewUserPerformance(
        userId: Long,
        languageCode: String,
        correctAnswer: Int,
        wrongAnswer: Int,
    )
    suspend fun getUserQuizPerformance(userId: Long)
    val generalUserStats: SharedFlow<List<GeneralUserStats>>
}