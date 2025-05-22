package com.zyuniversita.domain.model.imagefinder
/**
 * Represents a raw image loaded in memory.
 *
 * This class holds essential metadata and binary content of an image,
 * commonly used for image processing or transmission (e.g., to a backend or ML model).
 *
 * @property fileName The name of the image file (e.g., "photo.jpg").
 * @property mimeType The MIME type of the image (e.g., "image/jpeg").
 * @property bytes The binary content of the image, stored as a [ByteArray].
 */
data class RawImage(
    val fileName: String,
    val mimeType: String,
    val bytes: ByteArray
) {
    /**
     * Compares this [RawImage] to another object for equality.
     * Byte arrays are compared using [contentEquals] to check content equivalence.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RawImage

        if (fileName != other.fileName) return false
        if (mimeType != other.mimeType) return false
        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    /**
     * Generates a hash code using the contents of the byte array
     * (via [contentHashCode]) for consistent hashing behavior.
     */
    override fun hashCode(): Int {
        var result = fileName.hashCode()
        result = 31 * result + mimeType.hashCode()
        result = 31 * result + bytes.contentHashCode()
        return result
    }
}
