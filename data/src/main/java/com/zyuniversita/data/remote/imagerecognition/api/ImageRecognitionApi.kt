package com.zyuniversita.data.remote.imagerecognition.api

import com.zyuniversita.data.remote.imagerecognition.model.ImageRecognitionResult
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * API interface for uploading images to perform recognition.
 *
 * This interface defines the endpoint used by Retrofit to send an image to a remote server
 * for image recognition purposes.
 *
 * The request is sent as a multipart form-data POST request.
 */
interface ImageRecognitionApi {

    /**
     * Uploads an image file to the server for recognition processing.
     *
     * The image is sent as a multipart request to the `/recognition/upload` endpoint.
     * The server is expected to process the image and return a [ImageRecognitionResult] object.
     *
     * @param image The image file wrapped in a [MultipartBody.Part] to be uploaded.
     *              This should include a valid media type (e.g., "image/jpeg").
     * @return A [Response] containing the result of the recognition process if successful.
     */
    @Multipart
    @POST("/recognition/upload")
    suspend fun uploadImageToRecognize(
        @Part image: MultipartBody.Part
    ): Response<ImageRecognitionResult>
}