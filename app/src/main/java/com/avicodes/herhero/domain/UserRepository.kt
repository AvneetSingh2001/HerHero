package com.avicodes.herhero.domain

import com.avicodes.herhero.data.models.Guardians
import com.avicodes.herhero.data.models.Users
import com.avicodes.herhero.data.utils.Response
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun addUser(uid: String, users: Users)
    suspend fun updateUser(uid: String, users: Users)
    suspend fun checkUser(uid: String) : Boolean
    suspend fun getUser(uid: String) : Users?
    suspend fun currentUser() : FirebaseUser
    suspend fun logoutUser()

    suspend fun updateUserGuardian(uid: String, gid: List<Guardians>)

    suspend fun saveGuardiansToDb(guardians: Guardians)

    fun getSavedGuardians(): Flow<List<Guardians>>

    suspend fun deleteGuardians(guardians: Guardians)
}