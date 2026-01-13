package com.example.academy_tbc.data.worker

import android.content.Context
import android.net.Uri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.academy_tbc.presentation.util.DateUtils
import com.google.firebase.storage.FirebaseStorage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.time.ExperimentalTime

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    private val firebaseStorage = FirebaseStorage.getInstance()

    @OptIn(ExperimentalTime::class)
    override suspend fun doWork(): Result {
        val filePath = inputData.getString(TEMP_FILE_PATH) ?: return Result.failure()
        val file = File(filePath)

        return withContext(Dispatchers.IO) {
            try {
                val path = "images/${DateUtils.getCurrentEpoch()}.jpg"
                val storageRef = firebaseStorage.reference.child(path)
                storageRef.putFile(Uri.fromFile(file)).await()
                Result.success()
            } catch (e: Exception) {
                Result.failure(Data.Builder().putString(ERROR_KEY, e.message).build())

            }
        }
    }

    companion object {
        const val ERROR_KEY = "error_key"
        const val TEMP_FILE_PATH = "upload_file_path"
    }

}