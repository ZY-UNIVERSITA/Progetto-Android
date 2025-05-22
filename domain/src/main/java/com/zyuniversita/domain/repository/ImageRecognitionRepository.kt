package com.zyuniversita.domain.repository

import com.zyuniversita.domain.model.imagefinder.RawImage

interface ImageRecognitionRepository {
    suspend fun uploadImage(image: RawImage): String?
}