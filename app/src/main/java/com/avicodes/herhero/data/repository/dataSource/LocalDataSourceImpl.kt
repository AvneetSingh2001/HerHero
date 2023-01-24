package com.avicodes.herhero.data.repository.dataSource

import com.avicodes.herhero.data.db.GuardiansDao
import com.avicodes.herhero.data.models.Guardians
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(
    private val guardiansDao: GuardiansDao
): LocalDataSource {
    override suspend fun saveGuardiansToDb(guardians: Guardians) {
        guardiansDao.insert(guardians)
    }

    override fun getSavedGuardians(): Flow<List<Guardians>> {
        return guardiansDao.getAllGuardians()
    }

    override suspend fun deleteGuardians(guardians: Guardians) {
        guardiansDao.delete(guardians)
    }

}