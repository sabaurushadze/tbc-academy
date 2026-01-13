package com.example.academy_tbc.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.net.toUri
import com.example.academy_tbc.domain.util.Compressor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImageCompressor @Inject constructor(
    @ApplicationContext private val context: Context,
) : Compressor {
    override suspend fun compressImage(uriString: String, percentage: Int): ByteArray? {
        val imageQualityPercentage = if (percentage in 1..100) percentage else 80
        val uri = uriString.toUri()
        return withContext(Dispatchers.IO) {
            val mimeType = context.contentResolver.getType(uri)
            val inputBytes = context
                .contentResolver
                .openInputStream(uri)?.use { inputStream ->
                    inputStream.readBytes()
                } ?: return@withContext null

            withContext(Dispatchers.Default) {
                val bitmap = BitmapFactory.decodeByteArray(inputBytes, 0, inputBytes.size)

                val compressFormat = when (mimeType) {
                    "image/png" -> Bitmap.CompressFormat.PNG
                    "image/jpeg" -> Bitmap.CompressFormat.JPEG
                    "image/webp" -> if (Build.VERSION.SDK_INT >= 30) Bitmap.CompressFormat.WEBP_LOSSLESS else Bitmap.CompressFormat.WEBP
                    else -> Bitmap.CompressFormat.JPEG
                }

                var outputBytes: ByteArray
                ByteArrayOutputStream().use { outputStream ->
                    bitmap.compress(compressFormat, imageQualityPercentage, outputStream)
                    outputBytes = outputStream.toByteArray()
                }

                outputBytes

            }
        }
    }
}