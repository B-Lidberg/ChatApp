package com.lid.chatapp

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.lid.chatapp.data.model.ChatMessage
import com.lid.chatapp.presentation.viewmodels.ChatViewModel
import com.lid.chatapp.presentation.MessageCard
import com.lid.chatapp.ui.theme.ChatAppTheme
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket
import io.socket.client.IO
import io.socket.emitter.Emitter
import kotlinx.coroutines.*
import java.net.URISyntaxException

const val TAG = "TAG"
val gson: Gson = Gson()

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mSocket: Socket

    private val viewModel: ChatViewModel by viewModels()

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            mSocket = IO.socket("ws://10.0.2.2:4444")
            Log.d(TAG, "Success - ${mSocket.id()}")

        } catch (e: URISyntaxException) {
            Log.e(TAG, "error: ${e.localizedMessage}")
        }
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on("disconnect", onConnectionError)
        mSocket.on("chat message", onMessage)
        mSocket.connect()


        setContent {
            ChatAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ChatApp(viewModel) { sendMessage(it) }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket.close()
        Log.d(TAG, "WebSocket Connection closed")
    }

    private val onConnect = Emitter.Listener {
            Log.e(TAG, "connected")
        mSocket.emit("subscribe", it)
    }
    private val onConnectionError = Emitter.Listener {
            Log.e(TAG, "connection error")
    }
    private val onMessage = Emitter.Listener {
        GlobalScope.launch(Dispatchers.IO) {
            val message: ChatMessage = gson.fromJson(it[0].toString(), ChatMessage::class.java)
            Log.d(TAG, "onMessage called for $message")
            viewModel.addMessage(ChatMessage(content = message.content))
        }
    }

    fun sendMessage(message: String) {
        if (message.isNullOrEmpty()) return
        val sendData = ChatMessage(content = message)
        val jsonData = gson.toJson(sendData)
        mSocket.emit("chat message", jsonData)
        viewModel.clearCurrentMessage()
    }
}


val fakeChat = listOf(
    ("first"),
    ("second"),
    ("third"),
)

@Composable
fun ChatApp(vm: ChatViewModel, sendMessage: (String) -> Unit) {
    val currentMessage by vm.messageText.observeAsState("")
    val messageList by vm.allMessages.observeAsState()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                TopAppBar(
                    backgroundColor = Color.White,
                    elevation = 1.dp,
                    title = {
                        Text("Chat App")
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                Firebase.auth.signOut()
                                vm.clearChatHistory()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            PostMessageButton() {
                Log.d(TAG, "current message: $currentMessage, message list: $messageList")
                sendMessage(currentMessage)
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            ChatBox(messageList ?: emptyList())
            OutlinedTextField(
                value = currentMessage,
                onValueChange = { vm.onMessageTextChange(it) },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun PostMessageButton(sendMessage: () -> Unit) {
    FloatingActionButton(
        onClick = {
            sendMessage()
            Log.d("TAG", "send message clicked")
        },
    ) {
        Text("Send")
    }
}

@Composable
fun ChatBox(messages: List<ChatMessage>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.75f),
        reverseLayout = true
    ) {
        items(items = messages.reversed()) { message ->
            MessageCard(
                message = message.content,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}
