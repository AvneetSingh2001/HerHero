package com.avicodes.herhero.presentation.ui.authScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avicodes.herhero.data.models.Users
import com.avicodes.herhero.data.utils.Response
import com.avicodes.herhero.data.utils.ValidateResponse
import com.avicodes.herhero.domain.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth
): ViewModel() {

    var flowState: MutableLiveData<ValidateResponse> = MutableLiveData(ValidateResponse())


    fun addUser(name: String, phone: String, location: String) {

        flowState = MutableLiveData(ValidateResponse())
        flowState.value = ValidateResponse(loading = true)

        Log.e("MYTAG", "$name $phone $location")

        if(name == "") {
            flowState.value = flowState.value?.copy(nameEmpty = true)
            Log.e("MYTAG", "Empty $name $phone $location")

        }

        if(phone.length != 10) {
            flowState.value = flowState.value?.copy(phoneNotValid = true)
        }

        if(phone == "") {
            flowState.value = flowState.value?.copy(phoneEmpty = true)
        }


        if(location == "") {
            flowState.value = flowState.value?.copy(locEmpty = true)
        }

        if(
            flowState.value?.success == false
            && flowState.value?.phoneEmpty == false
            && flowState.value?.phoneNotValid == false
            && flowState.value?.nameEmpty == false
            && flowState.value?.locEmpty == false
            && flowState.value?.loading == true
        ) {
            val id = "G-$phone"
            val users = Users(id = auth.currentUser!!.uid, name = name, phone = phone, location = location)
            viewModelScope.launch(Dispatchers.IO) {
                userRepository.addUser(id, users)
            }
            Log.e("MYTAG", "adduser")
            flowState.value = flowState.value?.copy( success = true)
        }


    }


    fun updateUser(name: String?, guardian: ArrayList<String>?, superGuard: String?, phone: String?, location: String?) {

        viewModelScope.launch(Dispatchers.IO) {
            //userRepository.updateUser(users.id,users)
        }
    }

    fun logooutUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.logoutUser()
        }
    }
}