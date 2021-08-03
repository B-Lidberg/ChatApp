package com.lid.chatapp.viewmodels

import androidx.lifecycle.*
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.chatapp.data.repositories.AccountRepo
import com.lid.chatapp.util.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
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

    fun signOut() {
        viewModelScope.launch {
            accountRepo.clearUserData()
            Firebase.auth.signOut()
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            accountRepo.setUserData(Firebase.auth.currentUser!!.email!!)
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun registerWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            accountRepo.setUserData(Firebase.auth.currentUser!!.email!!).wait()
            loadingState.emit(LoadingState.LOADED)

        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
            accountRepo.setUserData(Firebase.auth.currentUser!!.email!!)
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun signInAsGuest(username: String) = viewModelScope.launch {
        loadingState.emit(LoadingState.LOADING)
        if (accountRepo.userDataFlow.first().username.isNullOrEmpty()) {
            accountRepo.setUserData(username)
        }
        loadingState.emit(LoadingState.LOADED)

    }

}

