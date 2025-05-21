package com.zyuniversita.data.remote.imagerecognition

import com.zyuniversita.data.remote.imagerecognition.api.ImageRecognitionApi
import com.zyuniversita.data.remote.imagerecognition.model.ImageRecognitionResult
import com.zyuniversita.domain.model.imagefinder.RawImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

/**
 * A data source interface for performing remote image recognition.
 *
 * This interface defines a single suspend function to upload an image and retrieve the recognition result.
 */
interface RemoteImageRecognitionDataSource {
    /**
     * Uploads the given [RawImage] to a remote service for image recognition.
     *
     * @param image The [RawImage] to be uploaded.
     * @return A [String] representing the recognized word if the operation is successful;
     *         otherwise, returns null.
     */
    suspend fun uploadImage(image: RawImage): String?
}

/**
 * An implementation of [RemoteImageRecognitionDataSource] that communicates with a remote image recognition API.
 *
 * This class uses an [ImageRecognitionApi] instance to perform the image upload and process the recognition result.
 *
 * @property imageRecognitionApi The API interface used for image recognition network operations.
 */
class RemoteImageRecognitionDataSourceImpl @Inject constructor(
    private val imageRecognitionApi: ImageRecognitionApi
) : RemoteImageRecognitionDataSource {

    /**
     * Uploads the provided [RawImage] to the remote image recognition service.
     *
     * The image is first converted into a [MultipartBody.Part] using the [RawImage.toMultipartBodyPart] extension function.
     * Then, the image is uploaded via [ImageRecognitionApi.uploadImageToRecognize]. The function returns
     * the recognized word from the response if successful, or null otherwise.
     *
     * @param image The [RawImage] to upload.
     * @return A [String] containing the recognized word, or null if the upload fails or the word is not recognized.
     */
    override suspend fun uploadImage(image: RawImage): String? {
        val multipartBody = image.toMultipartBodyPart()
        val response: Response<ImageRecognitionResult> =
            imageRecognitionApi.uploadImageToRecognize(multipartBody)

        return response.body()?.word
    }

    /**
     * Converts a [RawImage] to a [MultipartBody.Part] suitable for a multipart/form-data upload.
     *
     * This extension function creates a [RequestBody] from the image bytes using its MIME type,
     * and then constructs a multipart body part with the form key "image" and the image file name.
     *
     * @receiver The [RawImage] to be converted.
     * @return A [MultipartBody.Part] representing the [RawImage] for upload.
     */
    private fun RawImage.toMultipartBodyPart(): MultipartBody.Part {
        val reqBody = bytes.toRequestBody(mimeType.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", fileName, reqBody)
    }
}