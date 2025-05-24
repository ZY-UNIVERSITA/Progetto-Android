package com.zyuniversita.data.repository

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.zyuniversita.data.worker.PlayAppNotificationWorker
import com.zyuniversita.domain.repository.WorkerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkerRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : WorkerRepository {
    companion object {
        private const val TAG = "Worker repository TAG"
    }

    private val workManager = WorkManager.getInstance(context)

    override fun addNotificationWorker(): Unit {
        val workerBuilder = OneTimeWorkRequestBuilder<PlayAppNotificationWorker>().setInitialDelay(
            24,
            TimeUnit.HOURS
        ).addTag(TAG)

        workManager.enqueue(workerBuilder.build())
    }

    override fun removeNotificationWorker(): Unit {
        workManager.cancelAllWorkByTag(TAG)
    }
}