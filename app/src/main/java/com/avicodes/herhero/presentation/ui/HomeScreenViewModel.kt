package com.avicodes.herhero.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.avicodes.herhero.data.models.Guardians
import com.avicodes.herhero.data.models.Users
import com.avicodes.herhero.domain.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth
): ViewModel() {

    fun getSavedGuardians() = liveData {
        userRepository.getSavedGuardians().collect {
            emit(it)
        }
    }


    fun getFirebaseUser() = auth.currentUser

    suspend fun getCurrentUserDetails() =
        viewModelScope.async(Dispatchers.IO) {
            userRepository.getUser(auth.uid!!)
        }.await()

    fun deleteGuardian(guardians: Guardians) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteGuardians(guardians)
        }
    }
}