package com.zyuniversita.ui.utils.mapper

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.zyuniversita.domain.model.imagefinder.RawImage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UriToRawImageMapper @Inject constructor(
    @ApplicationContext private val appContext: Context
) {

    suspend fun map(uri: Uri): RawImage = withContext(Dispatchers.IO) {
        val cr: ContentResolver = appContext.contentResolver

        val mimeType = cr.getType(uri) ?: "image/*"
        val fileName = "image_${System.currentTimeMillis()}"

        val bytes = cr.openInputStream(uri)!!.use { it.readBytes() }

        RawImage(
            fileName = fileName,
            mimeType = mimeType,
            bytes = bytes
        )
    }
}