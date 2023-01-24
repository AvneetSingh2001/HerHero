package com.avicodes.herhero.data.repository.dataSource

import com.avicodes.herhero.data.models.Guardians
import com.avicodes.herhero.data.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class UserDataSourceImpl(
    private val firestoreDb: FirebaseFirestore,
    private val auth: FirebaseAuth
): UserDataSource {

    override suspend fun addUser(uid: String, users: Users) {
        firestoreDb.collection("Users").document(uid).set(users)
    }

    override suspend fun updateUser(uid: String, users: Users) {
        firestoreDb.collection("Users").document(uid).set(users)
    }

    override suspend fun checkUser(uid: String): Boolean = coroutineScope {
        val doc = async(Dispatchers.IO) {
            firestoreDb.collection("Users").document(uid).get()
        }.await()

        return@coroutineScope doc.await().exists()
    }

    override suspend fun getUser(uid: String): Users? = coroutineScope {
        val doc = async(Dispatchers.IO) { firestoreDb.collection("Users").document(uid).get() }.await()
        return@coroutineScope doc.await().toObject(Users::class.java)
    }

    override suspend fun currentUser(): FirebaseUser {
        return auth.currentUser!!
    }

    override suspend fun updateUserGuardian(uid: String, gidList: List<Guardians>) {
        firestoreDb.collection("Users").document(uid).update("guardians", gidList)
    }

    override suspend fun logoutUser() {
        auth.signOut()
    }

}