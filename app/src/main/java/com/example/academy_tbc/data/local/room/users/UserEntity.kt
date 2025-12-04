package com.example.academy_tbc.data.local.room.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "full_name") val fullName: String,
    val email: String,
    @ColumnInfo(name = "activation_status") val activationStatus: Int,
    @ColumnInfo(name = "profile_image_url") val profileImageUrl: String?,
)