package com.lid.chatapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.lid.chatapp.data.model.ChatMessage
import com.lid.chatapp.data.repositories.ChatRepo
import com.lid.chatapp.gson
import com.lid.chatapp.util.Constants
import com.lid.chatapp.util.Constants.PORT
import com.lid.chatapp.util.Constants.WEBSOCKET_LOCALHOST
import com.lid.chatapp.util.Constants.WEBSOCKET_PHONE_IP
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URISyntaxException
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repo: ChatRepo
) : ViewModel() {

    private lateinit var mSocket: Socket

    private val onConnect = Emitter.Listener {
        Log.e(Constants.TAG, "connected")
        mSocket.emit("subscribe", it)
    }
    private val onConnectionError = Emitter.Listener {
        Log.e(Constants.TAG, "connection error")
    }
    private val onMessage = Emitter.Listener {
        GlobalScope.launch(Dispatchers.IO) {
            val message: ChatMessage = gson.fromJson(it[0].toString(), ChatMessage::class.java)
            Log.d(Constants.TAG, "onMessage called for $message")
            addMessage(ChatMessage(content = message.content))
        }
    }

    fun sendMessage(message: String) {
        if (message.isNullOrEmpty()) return
        val sendData = ChatMessage(content = message)
        val jsonData = gson.toJson(sendData)
        mSocket.emit("chat message", jsonData)
        clearCurrentMessage()
    }

    init {
        try {
            mSocket = io.socket.client.IO.socket(WEBSOCKET_LOCALHOST + PORT)
            mSocket = io.socket.client.IO.socket(WEBSOCKET_PHONE_IP + PORT)

            Log.d(Constants.TAG, "Success on emulator - ${mSocket.id()}")

        } catch (e: URISyntaxException) {
            Log.e(Constants.TAG, "error: ${e.localizedMessage}")
        }

        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on("disconnect", onConnectionError)
        mSocket.on("chat message", onMessage)
        mSocket.connect()
    }

    override fun onCleared() {
        super.onCleared()
        mSocket.close()
        Log.d(Constants.TAG, "WebSocket Connection closed")
    }

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
