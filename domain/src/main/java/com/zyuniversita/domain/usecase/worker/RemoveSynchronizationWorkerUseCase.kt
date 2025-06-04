package com.zyuniversita.domain.usecase.worker

import com.zyuniversita.domain.repository.WorkerRepository
import javax.inject.Inject

interface RemoveSynchronizationWorkerUseCase {
    operator fun invoke(): Unit
}

class RemoveSynchronizationWorkerUseCaseImpl @Inject constructor(private val workerRepository: WorkerRepository) :
    RemoveSynchronizationWorkerUseCase {
    override fun invoke() {
        workerRepository.removeSynchronizeDataWorker()
    }
}
