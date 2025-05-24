package com.zyuniversita.data.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PlayAppNotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val notificationManager: NotificationManager,
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val TAG = "Notify_worker_TAG"
    }

    override suspend fun doWork(): Result {
        return try {
            showNotification()
            Log.d(TAG, "Doing the work")

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error occurred while trying to do the work.")

            Result.failure()
        }
    }

    private fun showNotification() {
        val channelId = "game_reminder_channel"

        // Notificatino channel is need from  API 26
        val channel = NotificationChannel(
            channelId,
            "Game Reminder",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val pendingIntent: PendingIntent? = createPendingIntent()

        pendingIntent?.let {
            val notification = Notification.Builder(applicationContext, channelId)
                .setContentTitle("New contents are available!")
                .setContentText("Open the app and learn new words and languages!")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            // id notification and notification
            notificationManager.notify(1, notification)
        } ?: Log.d(TAG, "Notification worker didn't start.")

    }

    private fun createPendingIntent(): PendingIntent? {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)

        return intent?.let {
            PendingIntent.getActivity(
                context,
                // codice di identificazione dell'intent
                0,
                it,
                PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }

}