package com.lid.chatapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.lid.chatapp.data.ChatMessage
import com.lid.chatapp.presentation.ChatViewModel
import com.lid.chatapp.ui.theme.ChatAppTheme
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket
import io.socket.client.IO
import io.socket.emitter.Emitter
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URISyntaxException

const val TAG = "TAG"
val gson: Gson = Gson()

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mSocket: Socket
    private lateinit var username: String

    private val viewModel: ChatViewModel by viewModels()

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            mSocket = IO.socket("http://localhost:4444")
            Log.d(TAG, "Success - connected")

        } catch (e: URISyntaxException) {
            Log.e(TAG, "error: ${e.localizedMessage}")
        }
        mSocket.connect()
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectionError)
        mSocket.on("newMessage", onMessage)

        setContent {
            ChatAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ChatApp(viewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "WebSocket Connection closed")
    }
}

@DelicateCoroutinesApi
val onConnect = Emitter.Listener {
    GlobalScope.launch(Dispatchers.IO) {
        Log.e(TAG, "connected")
    }
}
@DelicateCoroutinesApi
val onConnectionError = Emitter.Listener {
    GlobalScope.launch(Dispatchers.IO) {
        Log.e(TAG, "connection error")

    }
}
@DelicateCoroutinesApi
val onMessage = Emitter.Listener { args ->
    val obj = JSONObject(args[0].toString())
    GlobalScope.launch(Dispatchers.IO) {
        Log.e(TAG,"${obj.get("name")}: ${obj.get("message")}")
    }
}

val fakeChat = listOf<ChatMessage>(
    ChatMessage("first"),
    ChatMessage("second"),
    ChatMessage("third"),
)

@Composable
fun ChatApp(vm: ChatViewModel) {

    val currentMessage by vm.messageText.observeAsState("")
    val messageList by vm.allMessages.observeAsState(vm.allMessages.value ?: mutableListOf())

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
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                Firebase.auth.signOut()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ExitToApp,
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
                vm.sendMessage(ChatMessage(currentMessage))
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.75f)) {
                items(items = messageList) { message ->
                    Column() {
                        Row(Modifier.fillMaxWidth()) {
                            Text(text = message.user ?: "", modifier = Modifier.padding(horizontal = 8.dp))
                            Text(text = message.content, modifier = Modifier.padding(horizontal = 8.dp))
                        }
                        Text(text = message.timeStamp.toString(), modifier = Modifier.padding(horizontal = 8.dp))

                    }
                }
            }
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
