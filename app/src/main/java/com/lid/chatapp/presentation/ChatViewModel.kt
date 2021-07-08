package com.lid.chatapp.presentation

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lid.chatapp.data.ChatMessage
import com.lid.chatapp.data.ChatRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repo: ChatRepo
) : ViewModel() {

    private val _allMessages = repo.getAllMessages().asLiveData()
    val allMessages: LiveData<List<ChatMessage>> = _allMessages

    private val _messageText: MutableLiveData<String> = MutableLiveData()
    val messageText: LiveData<String> = _messageText


    fun onMessageTextChange(text: String) {
        _messageText.postValue(text)
    }

    fun clearCurrentMessage() {
       _messageText.value = ""
    }

    fun addMessage(message: ChatMessage) {
        viewModelScope.launch {
            repo.insertMessage(message)
        }
    }

    fun clearChatHistory() {
        GlobalScope.launch(IO) {
            repo.clearMessages()
        }
    }

}
