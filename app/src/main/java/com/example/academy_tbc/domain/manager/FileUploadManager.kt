package com.example.academy_tbc.domain.manager

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource

interface FileUploadManager {
    suspend fun enqueueFileUpload(byteArray: ByteArray): Resource<String, DataError.Firestore>
}