package com.avicodes.herhero.data.repository.dataSource

import com.avicodes.herhero.data.models.Users
import com.avicodes.herhero.data.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlin.system.exitProcess

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
        val doc = async {firestoreDb.collection("Users").document(uid).get()}
        val exists = doc.await().result.exists()
        return@coroutineScope exists
    }

    override suspend fun getUser(uid: String): Users? = coroutineScope {
        val doc = async { firestoreDb.collection("Users").document(uid).get()}
        val result = doc.await().result
        val user = result.toObject(Users::class.java)
        return@coroutineScope user
    }

    override suspend fun currentUser(): FirebaseUser {
        return auth.currentUser!!
    }

    override suspend fun logoutUser() {
        auth.signOut()
    }

}