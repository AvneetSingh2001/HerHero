package com.avicodes.herhero.data.repository

import com.avicodes.herhero.data.models.Users
import com.avicodes.herhero.data.repository.dataSource.UserDataSource
import com.avicodes.herhero.data.utils.Response
import com.avicodes.herhero.domain.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
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
}