package com.zyuniversita.domain.repository

import com.zyuniversita.domain.model.userdata.GeneralUserStats
import com.zyuniversita.domain.model.userdata.GeneratedWordsStats
import com.zyuniversita.domain.model.userdata.UserQuizPerformanceStats
import com.zyuniversita.domain.model.userdata.WordsUserData
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface UserDataRepository {
    suspend fun updateSelectedWord(wordsList: Map<Int, Boolean>)
    suspend fun updateUserData(wordlist: Map<Int, GeneratedWordsStats>)
    suspend fun insertNewUser(userId: Long?, username: String): Long
    suspend fun updateNewUserPerformance(
        userId: Long,
        languageCode: String,
        correctAnswer: Int,
        wrongAnswer: Int,
    )

    suspend fun getUserQuizPerformance(userId: Long)
    val generalUserStats: SharedFlow<List<GeneralUserStats>>

    suspend fun getWordsUserData()
    val wordsUserData: StateFlow<List<WordsUserData>>

    suspend fun deleteWordsDataByUserID(userID: Long)
    suspend fun deleteUserDataByUserID(userID: Long)

    suspend fun insertAllUserDataByUserID(userID: Long, userData: List<UserQuizPerformanceStats>)
    suspend fun insertAllWordsDataByUserID(userID: Long, wordsData: List<WordsUserData>)

    suspend fun getUserDataQuizPerformanceByUserID(userID: Long)
    val userQuizPerformance: StateFlow<List<UserQuizPerformanceStats>>

}