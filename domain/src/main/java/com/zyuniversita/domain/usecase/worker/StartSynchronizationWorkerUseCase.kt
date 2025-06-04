package com.zyuniversita.domain.usecase.worker

import com.zyuniversita.domain.repository.WorkerRepository
import javax.inject.Inject

interface StartSynchronizationWorkerUseCase: () -> Unit

class StartSynchronizationWorkerUseCaseImpl @Inject constructor(private val workerRepository: WorkerRepository): StartSynchronizationWorkerUseCase {
    override fun invoke() {
        workerRepository.addSynchronizeDataWorker()
    }

}