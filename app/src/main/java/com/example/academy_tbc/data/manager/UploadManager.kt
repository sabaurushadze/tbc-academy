package com.example.academy_tbc.data.manager

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.academy_tbc.data.worker.UploadWorker
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.manager.FileUploadManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import java.io.File
import javax.inject.Inject

class UploadManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : FileUploadManager {

    override suspend fun enqueueFileUpload(byteArray: ByteArray): Resource<String, DataError.Firestore> {
        val file = File(context.cacheDir, "image_${System.currentTimeMillis()}.jpg")
        file.writeBytes(byteArray)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(workDataOf(UploadWorker.TEMP_FILE_PATH to file.absolutePath))
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.beginUniqueWork(
            UPLOAD_WORKER,
            ExistingWorkPolicy.REPLACE,
            workRequest
        ).enqueue()


        val finishedWorkInfo = workManager.getWorkInfoByIdFlow(workRequest.id)
            .filterNotNull()
            .first { it.state.isFinished }

        return handleUploadUpdates(finishedWorkInfo)

    }

    private fun handleUploadUpdates(workInfo: WorkInfo): Resource<String, DataError.Firestore> {
        return when (workInfo.state) {
            WorkInfo.State.SUCCEEDED -> {
                Resource.Success("")
            }

            else -> Resource.Failure(error = DataError.Firestore.Unknown)
        }
    }

    companion object {
        private const val UPLOAD_WORKER = "upload_worker"
    }
}