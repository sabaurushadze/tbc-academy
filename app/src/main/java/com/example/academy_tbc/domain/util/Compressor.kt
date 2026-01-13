package com.example.academy_tbc.domain.util

interface Compressor {
    suspend fun compressImage(uriString: String, percentage: Int): ByteArray?
}