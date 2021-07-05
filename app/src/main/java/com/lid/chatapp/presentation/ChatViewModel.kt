package com.lid.chatapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
) : ViewModel() {

    private val _allMessages: MutableLiveData<MutableList<String>> = MutableLiveData()
    val allMessages: LiveData<MutableList<String>> = _allMessages

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message


    fun onMessageChange(text: String) {
        _message.postValue(text)
    }

    fun sendMessage(message: String) {
       viewModelScope.launch {
           _allMessages.value?.add(message)
           _message.value = ""
       }
    }


}
