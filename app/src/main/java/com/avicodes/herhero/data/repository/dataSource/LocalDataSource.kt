package com.avicodes.herhero.data.repository.dataSource

import com.avicodes.herhero.data.models.Guardians
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun saveGuardiansToDb(guardians: Guardians)

    fun getSavedGuardians(): Flow<List<Guardians>>

    suspend fun deleteGuardians(guardians: Guardians)
}