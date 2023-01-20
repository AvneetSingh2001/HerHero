package com.avicodes.herhero.presentation.ui.authScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avicodes.herhero.data.models.Users
import com.avicodes.herhero.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    fun addUser(users: Users) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUser(users.id, users)
        }
    }

}