package com.example.academy_tbc.domain.usecase.upload_image

import com.example.academy_tbc.domain.manager.FileUploadManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val uploadManager: FileUploadManager
) {
    suspend operator fun invoke(byteArray: ByteArray) = withContext(Dispatchers.IO) {
        uploadManager.enqueueFileUpload(byteArray)
    }
}