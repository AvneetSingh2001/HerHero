package com.avicodes.herhero.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avicodes.herhero.domain.UserRepository
import com.google.firebase.auth.FirebaseAuth

class AddGuardianViewModelFactory(
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddGuardianViewModel(userRepository, auth) as T
    }
}