package com.zyuniversita.ui.findcharacter

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.usecase.imagerecognition.UploadImageToRecognizeUseCase
import com.zyuniversita.ui.utils.mapper.UriToRawImageMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindCharacterViewModel @Inject constructor(
    private val mapper: UriToRawImageMapper,
    private val uploadImageToRecognizeUseCase: UploadImageToRecognizeUseCase,
) : ViewModel() {
    private var _tempImageUri: Uri? = null
    val tempImageUri: Uri? get() = _tempImageUri

    private val _imageRecognized: MutableSharedFlow<String?> = MutableSharedFlow(0)
    val imageRecognized: SharedFlow<String?> = _imageRecognized.asSharedFlow()

    fun setImageUri(uri: Uri) {
        _tempImageUri = uri
    }

    fun uploadImage() {
        viewModelScope.launch {
            _tempImageUri?.let {
                val rawImage = mapper.map(_tempImageUri!!)
                val result = runCatching { uploadImageToRecognizeUseCase(rawImage) }

                result.onSuccess { response ->
                    response?.let {
                        _tempImageUri = null
                    }

                    _imageRecognized.emit(response)
                }.onFailure {
                    Log.d("Image recognition", "Internet error or server is not responding.")
                    _imageRecognized.emit("Error. Try again.")
                }


            }
        }
    }
}