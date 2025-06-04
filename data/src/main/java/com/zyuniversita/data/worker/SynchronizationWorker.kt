package com.zyuniversita.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.zyuniversita.domain.model.synchronization.UserDataToSynchronize
import com.zyuniversita.domain.model.userdata.UserQuizPerformanceStats
import com.zyuniversita.domain.model.userdata.WordsUserData
import com.zyuniversita.domain.usecase.preferences.FetchUserIdUseCase
import com.zyuniversita.domain.usecase.synchronization.UploadUserData
import com.zyuniversita.domain.usecase.userdata.FetchUserQuizPerformanceUseCase
import com.zyuniversita.domain.usecase.userdata.FetchWordsUserDataUseCase
import com.zyuniversita.domain.usecase.userdata.StartFetchUserQuizPerformanceUseCase
import com.zyuniversita.domain.usecase.userdata.StartFetchWordsUserDataUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltWorker
class SynchronizationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val fetchUserIdUseCase: FetchUserIdUseCase,

    private val startFetchUserQuizPerformanceUseCase: StartFetchUserQuizPerformanceUseCase,
    private val fetchUserQuizPerformanceUseCase: FetchUserQuizPerformanceUseCase,

    private val startFetchWordsUserDataUseCase: StartFetchWordsUserDataUseCase,
    private val fetchWordsUserDataUseCase: FetchWordsUserDataUseCase,

    private val uploadUserData: UploadUserData
) : CoroutineWorker(context, workerParams) {
    companion object {
        private const val TAG = "Synchronization_Worker_TAG"
    }

    private lateinit var userDataToSynchronize: UserDataToSynchronize

    private val scope = CoroutineScope(Dispatchers.Main)

    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "Doing the work")
            getData()

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error occurred while trying to do the work.")

            Result.failure()
        }
    }

    private suspend fun getData() {
        val userId = fetchUserIdUseCase().first()

        userId?.let { userID ->
            scope.launch {
                val userData = async { fetchGeneralData(userID) }
                val wordsData = async { fetchWordsData() }

                userDataToSynchronize = UserDataToSynchronize(
                    userId = userID,
                    userData = userData.await(),
                    wordsUserData = wordsData.await()
                )

                uploadUserData(userDataToSynchronize)
            }

        }
    }

    private suspend fun fetchGeneralData(userId: Long): List<UserQuizPerformanceStats> {
        startFetchUserQuizPerformanceUseCase(userId)
         return fetchUserQuizPerformanceUseCase().first()
    }

    private suspend fun fetchWordsData(): List<WordsUserData> {
        startFetchWordsUserDataUseCase()
        return fetchWordsUserDataUseCase().first()
    }
}