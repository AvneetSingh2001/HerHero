package com.avicodes.herhero.presentation.ui.authScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avicodes.herhero.data.models.Users
import com.avicodes.herhero.data.utils.Response
import com.avicodes.herhero.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    fun addUser(users: Users) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUser(users.id, users)
        }
    }

    var checkUserData: MutableLiveData<Response> = MutableLiveData(Response.NotInitialized)
    fun checkUser(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        checkUserData.postValue(Response.Loading("loading"))

        val check = async{return@async userRepository.checkUser(uid)}.await()
        Log.e("MYTAG", check.toString())

        try {
            if (check) {
                checkUserData.postValue(Response.Success("Exists"))
            } else {
                checkUserData.postValue(Response.Success("Not Exists"))
            }
        } catch (e: Exception) {
            checkUserData.postValue(Response.Error(e))
            Log.e("MYTAG", e.toString())
        }

    }
}