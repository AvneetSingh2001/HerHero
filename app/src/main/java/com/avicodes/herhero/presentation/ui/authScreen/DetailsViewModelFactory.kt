package com.avicodes.herhero.presentation.ui.authScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avicodes.herhero.domain.UserRepository

class DetailsViewModelFactory(
private val userRepository: UserRepository
) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailsViewModel(userRepository) as T
        }
}