package com.example.academy_tbc.presentation.screen.image_manager.image_selector

import android.net.Uri

sealed class ImageSelectorEvent {
    data object ProcessImage : ImageSelectorEvent()
    data class OnUriCreated(val uri: Uri) : ImageSelectorEvent()
    data object UploadImage : ImageSelectorEvent()
}