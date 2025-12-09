package com.example.academy_tbc.data.room.home.post

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Upsert
    suspend fun insertUsers(vararg posts: PostEntity)

    @Query("SELECT * FROM postentity")
    fun getAll(): Flow<List<PostEntity>>
}