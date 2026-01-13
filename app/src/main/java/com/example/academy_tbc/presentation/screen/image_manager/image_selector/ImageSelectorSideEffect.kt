package com.example.academy_tbc.presentation.screen.image_manager.image_selector

sealed interface ImageSelectorSideEffect {
    data object SuccessfullyUploaded : ImageSelectorSideEffect
    data object UploadFailed : ImageSelectorSideEffect
}