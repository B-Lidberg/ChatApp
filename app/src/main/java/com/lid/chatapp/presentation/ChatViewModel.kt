package com.lid.chatapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lid.chatapp.data.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
) : ViewModel() {

    private val _allMessages: MutableLiveData<MutableList<ChatMessage>> = MutableLiveData()
    val allMessages: LiveData<MutableList<ChatMessage>> = _allMessages

    private val _message: MutableLiveData<ChatMessage> = MutableLiveData()
    val message: LiveData<ChatMessage> = _message

    private val _messageText: MutableLiveData<String> = MutableLiveData()
    val messageText: LiveData<String> = _messageText


    fun onMessageTextChange(text: String) {
        _messageText.postValue(text)
    }

    fun sendMessage(message: ChatMessage) {
       viewModelScope.launch {
           _allMessages.value?.add(message)
           _messageText.value = ""
           _message.value = null
       }
    }

}
