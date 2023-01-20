package com.avicodes.herhero.data.repository.dataSource

import com.avicodes.herhero.data.models.Users
import com.avicodes.herhero.data.utils.Response
import com.google.firebase.auth.FirebaseUser

interface UserDataSource {
    suspend fun addUser(uid: String, users: Users)
    suspend fun updateUser(uid: String, users: Users)
    suspend fun checkUser(uid: String) : Boolean
    suspend fun getUser(uid: String) : Users?
    suspend fun currentUser() : FirebaseUser
    suspend fun logoutUser()
}