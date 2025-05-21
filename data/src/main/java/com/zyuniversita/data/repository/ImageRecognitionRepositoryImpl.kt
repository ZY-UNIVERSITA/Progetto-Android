package com.zyuniversita.data.repository

import com.zyuniversita.data.remote.imagerecognition.RemoteImageRecognitionDataSource
import com.zyuniversita.domain.model.imagefinder.RawImage
import com.zyuniversita.domain.repository.ImageRecognitionRepository
import javax.inject.Inject

/**
 * Implementation of the [ImageRecognitionRepository] interface.
 *
 * This repository delegates the image recognition upload operation to a [RemoteImageRecognitionDataSource].
 *
 * @property remoteImageRecognitionDataSource The remote data source responsible for performing the image recognition API call.
 */
class ImageRecognitionRepositoryImpl @Inject constructor(
    private val remoteImageRecognitionDataSource: RemoteImageRecognitionDataSource
) : ImageRecognitionRepository {

    /**
     * Uploads an image to be recognized by the remote image recognition service.
     *
     * This method delegates the call to [RemoteImageRecognitionDataSource.uploadImage] and returns
     * the recognized word from the response if successful, or null otherwise.
     *
     * @param image The [RawImage] to be uploaded for recognition.
     * @return A [String] representing the recognized word from the remote service, or null if the upload or recognition fails.
     */
    override suspend fun uploadImage(image: RawImage): String? {
        return remoteImageRecognitionDataSource.uploadImage(image)
    }
}