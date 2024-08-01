package com.dcs.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dcs.data.local.room.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users ORDER BY id ASC LIMIT 1")
    fun getFirstUser(): Flow<User?>

    @Delete
    fun delete(user: User)
}
