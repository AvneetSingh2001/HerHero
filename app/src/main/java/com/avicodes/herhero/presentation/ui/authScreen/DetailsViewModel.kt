package com.avicodes.herhero.presentation.ui.authScreen

import android.util.Log
import androidx.lifecycle.*
import com.avicodes.herhero.data.models.Guardians
import com.avicodes.herhero.data.models.Users
import com.avicodes.herhero.data.utils.Response
import com.avicodes.herhero.data.utils.ValidateResponse
import com.avicodes.herhero.domain.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth
): ViewModel(){

    var flowState: MutableLiveData<ValidateResponse> = MutableLiveData(ValidateResponse())
    var checkUserData: MutableLiveData<Response> = MutableLiveData(Response.NotInitialized)

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
            val users = Users(id = auth.currentUser!!.uid, name = name, phone = phone, location = location)
            viewModelScope.launch(Dispatchers.IO) {
                userRepository.addUser(users.id, users)
            }
            Log.e("MYTAG", "adduser")
            flowState.value = flowState.value?.copy( success = true)
        }
    }

    suspend fun updateLocalListGuardian(gid: String) = coroutineScope {
        val user = async(Dispatchers.IO) {
            userRepository.getUser(gid)
        }.await()
        Log.e("MYTAG", gid + " "+ user?.name)

        user?.let {
            var guardians = Guardians(gid, user.name.toString(), false, user.phone.toString())
            userRepository.saveGuardiansToDb(guardians)
        }

    }


    fun getSavedGuardians() = liveData{
        userRepository.getSavedGuardians().collect {
            emit(it)
        }
    }

    fun updateUserGuardian(guardianList: List<Guardians>) {
        viewModelScope.launch(Dispatchers.IO) {
            for( it in guardianList) {
                Log.e("list item" , it.name)
            }
            userRepository.updateUserGuardian(auth.uid!!, guardianList)
        }
    }

    fun updateUser(name: String?, guardian: List<String>?, superGuard: String?, phone: String?, location: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            //userRepository.updateUser(users.id,users)
        }
    }

    fun logooutUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.logoutUser()
        }
    }


    fun checkUser(uid: String) = viewModelScope.launch {

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

    fun deleteGuardian(guardians: Guardians) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteGuardians(guardians)
        }
    }

}