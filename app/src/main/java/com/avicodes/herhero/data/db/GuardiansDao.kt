package com.avicodes.herhero.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avicodes.herhero.data.models.Guardians
import kotlinx.coroutines.flow.Flow

@Dao
interface GuardiansDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(guardians: Guardians)

    @Delete
    suspend fun delete(guardians: Guardians)

    @Query("SELECT * FROM guardians_table")
    fun getAllGuardians(): Flow<List<Guardians>>
}