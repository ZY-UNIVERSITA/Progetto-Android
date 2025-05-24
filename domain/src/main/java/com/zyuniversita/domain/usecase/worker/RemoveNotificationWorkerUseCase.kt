package com.zyuniversita.domain.usecase.worker

import com.zyuniversita.domain.repository.WorkerRepository
import javax.inject.Inject

interface RemoveNotificationWorkerUseCase {
    operator fun invoke(): Unit
}

class RemoveNotificationWorkerUseCaseImpl @Inject constructor(private val workerRepository: WorkerRepository) :
    RemoveNotificationWorkerUseCase {
    override operator fun invoke() {
        workerRepository.removeNotificationWorker()
    }

}