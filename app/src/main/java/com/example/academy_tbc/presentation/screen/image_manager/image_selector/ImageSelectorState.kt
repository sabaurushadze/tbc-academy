package com.example.academy_tbc.presentation.screen.image_manager.image_selector

import android.net.Uri
import com.example.academy_tbc.presentation.util.ByteArrayHolder

data class ImageSelectorState(
    val uploading: Boolean = false,
    val compressedByteArray: ByteArrayHolder? = null,
    val imageUri: Uri? = null,
)