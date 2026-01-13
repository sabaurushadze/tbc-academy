package com.example.academy_tbc.domain.usecase.compressor

import com.example.academy_tbc.domain.util.Compressor
import javax.inject.Inject

class CompressImageUseCase @Inject constructor(
    private val imageCompressor: Compressor,
) {
    suspend operator fun invoke(uriString: String, percentage: Int): ByteArray? {
        return imageCompressor.compressImage(uriString = uriString, percentage = percentage)
    }
}