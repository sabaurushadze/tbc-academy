package com.example.academy_tbc.data.local.room.users

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun insertUsers(vararg users: UserEntity)

    @Query("SELECT * FROM userentity")
    fun getAll(): Flow<List<UserEntity>>


    @Query("DELETE FROM userentity WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)
}