package com.lid.chatapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lid.chatapp.data.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
) : ViewModel() {

    private val _sharedMessages = MutableSharedFlow<String>() // 1
    val sharedMessages = _sharedMessages.asSharedFlow() // 2

    fun emitSharedMessages() {
        viewModelScope.launch {
            for (message in allMessages.value ?: emptyList()) {
                _sharedMessages.emit(message)
            }
        }
    }

    private val _allMessages: MutableLiveData<MutableList<String>> = MutableLiveData()
    val allMessages: LiveData<MutableList<String>> = _allMessages

    private val _message: MutableLiveData<ChatMessage> = MutableLiveData()
    val message: LiveData<ChatMessage> = _message

    private val _messageText: MutableLiveData<String> = MutableLiveData()
    val messageText: LiveData<String> = _messageText


    fun onMessageTextChange(text: String) {
        _messageText.postValue(text)
    }

    fun sendMessage(message: String) {
       viewModelScope.launch {
           _allMessages.value?.add(message)
           _messageText.value = ""
           _message.value = null
       }
    }

}
