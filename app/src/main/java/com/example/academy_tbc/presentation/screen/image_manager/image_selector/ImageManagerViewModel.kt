package com.example.academy_tbc.presentation.screen.image_manager.image_selector

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.onFailure
import com.example.academy_tbc.domain.common.onSuccess
import com.example.academy_tbc.domain.usecase.compressor.CompressImageUseCase
import com.example.academy_tbc.domain.usecase.upload_image.UploadImageUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.util.ByteArrayHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageManagerViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase,
    private val compressImageUseCase: CompressImageUseCase,
) : BaseViewModel<ImageSelectorState, ImageSelectorSideEffect, ImageSelectorEvent>(
    ImageSelectorState()
) {

    override fun onEvent(event: ImageSelectorEvent) {
        when (event) {
            is ImageSelectorEvent.OnUriCreated -> {
                updateState { copy(imageUri = event.uri) }
            }

            is ImageSelectorEvent.ProcessImage -> {
                compressImage()
            }

            ImageSelectorEvent.UploadImage -> {
                uploadImage()
            }
        }
    }

    override fun setLoading(isLoading: Boolean) = updateState { copy(uploading = isLoading) }

    private fun uploadImage() = launchWithLoading {
        val byteArr = state.value.compressedByteArray?.byteArray
        byteArr?.let {
            uploadImageUseCase(it)
                .onSuccess { emitSideEffect(ImageSelectorSideEffect.SuccessfullyUploaded) }
                .onFailure { emitSideEffect(ImageSelectorSideEffect.UploadFailed) }
        }

    }

    private fun compressImage() {
        viewModelScope.launch(Dispatchers.IO) {
            val uriString = state.value.imageUri.toString()
            compressImageUseCase(uriString, IMAGE_QUALITY_PERCENTAGE)?.let { byteArray ->
                updateState { copy(compressedByteArray = ByteArrayHolder(byteArray)) }
            }
        }
    }

    companion object {
        private const val IMAGE_QUALITY_PERCENTAGE = 80
    }
}