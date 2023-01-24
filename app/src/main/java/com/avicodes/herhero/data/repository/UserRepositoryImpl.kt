package com.avicodes.herhero.data.repository

import com.avicodes.herhero.data.models.Guardians
import com.avicodes.herhero.data.models.Users
import com.avicodes.herhero.data.repository.dataSource.LocalDataSource
import com.avicodes.herhero.data.repository.dataSource.UserDataSource
import com.avicodes.herhero.data.utils.Response
import com.avicodes.herhero.domain.UserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val localDataSource: LocalDataSource
): UserRepository {


    override suspend fun addUser(uid: String, user: Users) {
        userDataSource.addUser(uid, user)
    }

    override suspend fun updateUser(uid: String, user: Users) {
        userDataSource.updateUser(uid, user)
    }

    override suspend fun checkUser(uid: String): Boolean {
        return userDataSource.checkUser(uid)
    }

    override suspend fun getUser(uid: String): Users? {
        return userDataSource.getUser(uid)
    }

    override suspend fun currentUser(): FirebaseUser {
        return userDataSource.currentUser()
    }

    override suspend fun logoutUser() {
        return userDataSource.logoutUser()
    }

    override suspend fun updateUserGuardian(uid: String, gid: List<Guardians>) {
        userDataSource.updateUserGuardian(uid, gid)
    }

    override suspend fun saveGuardiansToDb(guardians: Guardians) {
        localDataSource.saveGuardiansToDb(guardians)
    }

    override fun getSavedGuardians(): Flow<List<Guardians>> {
        return localDataSource.getSavedGuardians()
    }

    override suspend fun deleteGuardians(guardians: Guardians) {
        localDataSource.deleteGuardians(guardians)
    }
}