package com.avicodes.herhero.presentation.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.avicodes.herhero.data.models.Guardians
import com.avicodes.herhero.data.utils.Response
import com.avicodes.herhero.domain.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class AddGuardianViewModel(
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth
): ViewModel() {

    var checkUserData: MutableLiveData<Response> = MutableLiveData(Response.NotInitialized)


    fun updateUserGuardian(guardianList: List<Guardians>) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUserGuardian(auth.uid!!, guardianList)
        }
    }


    fun checkUser(uid: String) = viewModelScope.launch {

        checkUserData.postValue(Response.Loading("loading"))

        if(uid == "") {
            checkUserData.postValue(Response.Success("NULL"))
            return@launch
        }

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

}
