package com.zyuniversita.domain.repository

interface WorkerRepository {
    fun addNotificationWorker()
    fun removeNotificationWorker()

    fun addSynchronizeDataWorker()
    fun removeSynchronizeDataWorker()
}