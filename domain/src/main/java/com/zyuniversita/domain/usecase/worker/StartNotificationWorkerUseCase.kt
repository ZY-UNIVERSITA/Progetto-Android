package com.zyuniversita.domain.usecase.worker

import com.zyuniversita.domain.repository.WorkerRepository
import javax.inject.Inject

interface StartNotificationWorkerUseCase : () -> Unit

class StartNotificationWorkerUseCaseImpl @Inject constructor(private val workerRepository: WorkerRepository) :
    StartNotificationWorkerUseCase {
    override fun invoke() {
        workerRepository.addNotificationWorker()
    }
}