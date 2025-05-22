package com.zyuniversita.domain.usecase.imagerecognition

import com.zyuniversita.domain.model.imagefinder.RawImage
import com.zyuniversita.domain.repository.ImageRecognitionRepository
import javax.inject.Inject


/**
 * Use case interface for uploading an image and receiving a recognition result.
 *
 * This is defined as a functional (SAM) interface extending [suspend] function type, 
 * making it easy to call directly as a lambda in coroutine contexts.
 *
 * Given a [RawImage], it handles the upload process and returns the resulting
 * recognition output (e.g., identified text, label, etc.) as a [String], or `null` if the operation fails.
 */
interface UploadImageToRecognizeUseCase : suspend (RawImage) -> String?

/**
 * Implementation of [UploadImageToRecognizeUseCase] that delegates the upload operation
 * to [ImageRecognitionRepository].
 *
 * It takes care of sending the [RawImage] to the image recognition backend service
 * and returning the result (such as recognized text or labels).
 *
 * @param imageRecognitionRepository The repository responsible for handling image recognition operations.
 */
class UploadImageToRecognizeUseCaseImpl @Inject constructor(
    private val imageRecognitionRepository: ImageRecognitionRepository
) : UploadImageToRecognizeUseCase {

    /**
     * Invokes the use case, uploading the provided [image] and returning the recognition result.
     *
     * @param image The image to be uploaded and processed.
     * @return A result string from the recognition process, or `null` on failure.
     */
    override suspend fun invoke(image: RawImage): String? {
        return imageRecognitionRepository.uploadImage(image)
    }
}
