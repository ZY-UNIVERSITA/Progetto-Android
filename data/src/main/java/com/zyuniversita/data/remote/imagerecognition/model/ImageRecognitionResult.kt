package com.zyuniversita.data.remote.imagerecognition.model

import com.squareup.moshi.JsonClass

/**
 * Represents the result returned by the image recognition API.
 *
 * This data class holds the output of a recognition process.
 *
 * It is annotated with `@JsonClass(generateAdapter = true)` to enable Moshi serialization/deserialization.
 *
 * @property word The word extracted from the image, if any. Can be `null` if no recognizable text was found.
 */
@JsonClass(generateAdapter = true)
data class ImageRecognitionResult(
    val word: String? = null
)