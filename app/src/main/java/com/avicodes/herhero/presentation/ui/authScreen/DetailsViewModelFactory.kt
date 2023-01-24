package com.avicodes.herhero.presentation.ui.authScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avicodes.herhero.domain.UserRepository
import com.google.firebase.auth.FirebaseAuth

class DetailsViewModelFactory(
private val userRepository: UserRepository,
private val auth: FirebaseAuth
) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailsViewModel(userRepository, auth) as T
        }
}