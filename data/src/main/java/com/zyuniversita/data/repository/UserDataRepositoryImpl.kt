package com.zyuniversita.data.repository

import android.content.Context
import com.zyuniversita.data.local.database.db.QuizDatabase
import com.zyuniversita.data.local.database.entities.UserInfoEntity
import com.zyuniversita.data.local.database.entities.UserQuizPerformanceEntity
import com.zyuniversita.data.local.database.entities.WordUserDataEntity
import com.zyuniversita.data.utils.mapper.DataMapper
import com.zyuniversita.domain.model.userdata.GeneralUserStats
import com.zyuniversita.domain.model.userdata.GeneratedWordsStats
import com.zyuniversita.domain.model.userdata.UserQuizPerformanceStats
import com.zyuniversita.domain.model.userdata.WordsUserData
import com.zyuniversita.domain.repository.UserDataRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [UserDataRepository] responsible for managing and storing user-related data,
 * such as word selection status, quiz performance, and user information.
 *
 * Interacts with the local Room database via DAOs and uses [DataMapper] to convert
 * database models into domain models when needed.
 *
 * @param context The application context used to initialize the database.
 * @param dataMapper Mapper used to convert database entities to domain models.
 */
class UserDataRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataMapper: DataMapper,
) : UserDataRepository {

    private val wordUserDataDao = QuizDatabase.getInstance(context = context).wordUserDataDao()
    private val userInfoDao = QuizDatabase.getInstance(context = context).userInfoDao()
    private val quizPerformanceDao =
        QuizDatabase.getInstance(context = context).userQuizPerformanceDao()

    private val _generalUserStats: MutableStateFlow<List<GeneralUserStats>> = MutableStateFlow(
        listOf()
    )

    /**
     * A flow that emits aggregated user quiz performance data mapped to [GeneralUserStats].
     */
    override val generalUserStats: StateFlow<List<GeneralUserStats>> =
        _generalUserStats.asStateFlow()


    private val _wordsUserData: MutableStateFlow<List<WordsUserData>> = MutableStateFlow(listOf())
    override val wordsUserData: StateFlow<List<WordsUserData>> = _wordsUserData.asStateFlow()

    private val _userQuizPerformance: MutableStateFlow<List<UserQuizPerformanceStats>> =
        MutableStateFlow(listOf())
    override val userQuizPerformance: StateFlow<List<UserQuizPerformanceStats>> =
        _userQuizPerformance.asStateFlow()

    /**
     * Updates the selection status of multiple words in the user's data.
     *
     * If a word doesn't exist in the database, it will be inserted. If it exists and the selection
     * status changed, it will be updated.
     *
     * @param wordsList A map where the key is the word ID and the value is the selection status.
     */
    override suspend fun updateSelectedWord(wordsList: Map<Int, Boolean>) {
        withContext(Dispatchers.IO) {
            wordsList.forEach { (wordId, isSelected) ->
                val result: WordUserDataEntity? = wordUserDataDao.getByWordID(wordId)

                if (result == null) {
                    wordUserDataDao.insert(
                        WordUserDataEntity(
                            wordId = wordId,
                            selected = isSelected
                        )
                    )
                } else {
                    if (result.selected != isSelected) {
                        wordUserDataDao.setSelected(wordId, isSelected)
                    }
                }
            }
        }
    }

    /**
     * Updates user interaction statistics (correct/incorrect counts) for a list of words.
     *
     * If the stats for a word do not exist, they are inserted.
     *
     * @param wordlist A map where the key is the word ID and the value is newly generated word stats.
     */
    override suspend fun updateUserData(wordlist: Map<Int, GeneratedWordsStats>) {
        withContext(Dispatchers.IO) {
            wordlist.forEach { (wordId, newData) ->
                val result: Int =
                    wordUserDataDao.updateUserData(wordId, newData.correct, newData.incorrect)

                if (result == 0) {
                    wordUserDataDao.insertUserData(wordId, newData.correct, newData.incorrect)
                }
            }
        }
    }

    /**
     * Inserts a new user into the database.
     *
     * @param username The username of the new user.
     * @return The ID of the newly inserted user.
     */
    override suspend fun insertNewUser(userId: Long?, username: String): Long {
        return withContext(Dispatchers.IO) {
            val userInfo: UserInfoEntity = userId?.let {
                UserInfoEntity(userId = userId.toInt(), username = username)
            } ?: UserInfoEntity(username = username)

            val result: Long = userInfoDao.insertUser(userInfo)
            result
        }
    }

    /**
     * Updates quiz performance data for a user and a specific language.
     *
     * If the user's performance record doesn't exist, it will be inserted.
     *
     * @param userId The ID of the user.
     * @param languageCode The language the quiz was taken in.
     * @param correctAnswer The number of correct answers.
     * @param wrongAnswer The number of incorrect answers.
     */
    override suspend fun updateNewUserPerformance(
        userId: Long,
        languageCode: String,
        correctAnswer: Int,
        wrongAnswer: Int,
    ) {
        withContext(Dispatchers.IO) {
            val result =
                quizPerformanceDao.updateUserData(userId, languageCode, correctAnswer, wrongAnswer)

            if (result == 0) {
                val newUser: UserQuizPerformanceEntity = UserQuizPerformanceEntity(
                    userId = userId.toInt(),
                    languageCode = languageCode,
                    correctAnswer = correctAnswer,
                    wrongAnswer = wrongAnswer
                )
                quizPerformanceDao.insertUser(newUser)
            }
        }
    }

    /**
     * Retrieves the quiz performance of a specific user, maps the result to domain models,
     * and emits the data through [generalUserStats].
     *
     * @param userId The ID of the user whose performance data should be retrieved.
     */
    override suspend fun getUserQuizPerformance(userId: Long) {
        withContext(Dispatchers.IO) {
            val userStatsList = quizPerformanceDao.getUserDataByUserId(userId)

            val mappedUserStatsList = withContext(Dispatchers.Default) {
                userStatsList.map(dataMapper::fromUserQuizPerformanceWithLanguageToGeneralUserStats)
            }

            _generalUserStats.emit(mappedUserStatsList)
        }
    }

    override suspend fun getWordsUserData() {
        withContext(Dispatchers.IO) {
            val userDataList = wordUserDataDao.getAllUserData()

            val mappedUserStatsList = withContext(Dispatchers.Default) {
                userDataList.map(dataMapper::fromWordUserDataEntityToWordsUserData)
            }

            _wordsUserData.emit(mappedUserStatsList)

        }
    }

    override suspend fun deleteWordsDataByUserID(userID: Long) {
        withContext(Dispatchers.IO) {
            wordUserDataDao.deleteAllEntries()
        }
    }

    override suspend fun deleteUserDataByUserID(userID: Long) {
        withContext(Dispatchers.IO) {
            quizPerformanceDao.deleteAllEntriesByUserID(userID)
        }
    }

    override suspend fun insertAllUserDataByUserID(
        userID: Long,
        userData: List<UserQuizPerformanceStats>,
    ) {
        withContext(Dispatchers.IO) {
            val mappedData = userData.map { data ->
                dataMapper.fromGeneralUserStatsToUserQuizPerformanceWithLanguageEntity(
                    userID,
                    data
                )
            }

            quizPerformanceDao.insertAll(mappedData)
        }
    }

    override suspend fun insertAllWordsDataByUserID(userID: Long, wordsData: List<WordsUserData>) {
        withContext(Dispatchers.IO) {
            val mappedData =
                wordsData.map(dataMapper::fromWordsUserDataToWordUserDataEntity)

            wordUserDataDao.insertAll(mappedData)
        }
    }

    override suspend fun getUserDataQuizPerformanceByUserID(userID: Long) {
        withContext(Dispatchers.IO) {
            val dataPerformance =
                quizPerformanceDao.getUserDataPerformanceByUserId(userID)

            val mappedData = withContext(Dispatchers.Default) {
                dataPerformance.map(dataMapper::fromUserQuizPerformanceEntityToUserQuizPerformanceStats)
            }

            _userQuizPerformance.emit(mappedData)
        }

    }
}
