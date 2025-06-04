package com.zyuniversita.data.repository

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.zyuniversita.data.worker.PlayAppNotificationWorker
import com.zyuniversita.data.worker.SynchronizationWorker
import com.zyuniversita.domain.repository.WorkerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkerRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : WorkerRepository {
    companion object {
        private const val TAG_NOTIFICATION = "Notification Worker TAG"
        private const val TAG_SYNCHRONIZATION = "Synchronization Worker TAG"
    }

    private val workManager = WorkManager.getInstance(context)

    override fun addNotificationWorker(): Unit {
        val workerBuilder = OneTimeWorkRequestBuilder<PlayAppNotificationWorker>().setInitialDelay(
            24,
            TimeUnit.HOURS
        ).addTag(TAG_NOTIFICATION)

        workManager.enqueue(workerBuilder.build())
    }

    override fun removeNotificationWorker(): Unit {
        workManager.cancelAllWorkByTag(TAG_NOTIFICATION)
    }

    override fun addSynchronizeDataWorker(): Unit {
//        val subitoBuilder = OneTimeWorkRequestBuilder<SynchronizationWorker>()
//
//        workManager.enqueue(subitoBuilder.build())

        // Exec only with internet connection
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workerBuilder = PeriodicWorkRequestBuilder<SynchronizationWorker>(24, TimeUnit.HOURS).setConstraints(constraints).addTag(TAG_SYNCHRONIZATION)

        workManager.enqueue(workerBuilder.build())
    }

    override fun removeSynchronizeDataWorker(): Unit {
        workManager.cancelAllWorkByTag(TAG_SYNCHRONIZATION)
    }
}