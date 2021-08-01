package com.lid.chatapp.viewmodels

import androidx.lifecycle.*
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.chatapp.data.repositories.AccountRepo
import com.lid.chatapp.util.Constants
import com.lid.chatapp.util.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountRepo: AccountRepo
) : ViewModel() {

    private val userDataFlow = accountRepo.userDataFlow

    val userData = userDataFlow.asLiveData()

    val currentUsername = userDataFlow.map { user ->
        user.username
    }.asLiveData()

    val loadingState = MutableStateFlow(LoadingState.IDLE)


    private fun setLoginBoolean() {
        val username = userData.value?.username
        val password = userData.value?.password
//        _signedIn.value =
//            username != Constants.NO_USERNAME &&
//                    !username.isNullOrEmpty() &&
//                    !password.isNullOrEmpty()
    }

    fun signOut() {
        viewModelScope.launch {
            accountRepo.clearUserData()
            Firebase.auth.signOut()
//            _signedIn.postValue(false)

        }
        setLoginBoolean()
    }

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
//            TODO("ISSUE #4: Store email in DataStore")
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun registerWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            loadingState.emit(LoadingState.LOADED)

        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
//            TODO("ISSUE #4: Store token in DataStore")
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun signInAsGuest(username: String) = viewModelScope.launch {
        loadingState.emit(LoadingState.LOADING)
        accountRepo.setUserData(username)
//        _signedIn.postValue(true)
        loadingState.emit(LoadingState.LOADED)
    }

}

