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
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.lid.chatapp.data.ChatMessage
import com.lid.chatapp.presentation.ChatViewModel
import com.lid.chatapp.ui.theme.ChatAppTheme
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

const val TAG = "TAG"
val gson: Gson = Gson()

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var ktorClient: HttpClient
    private lateinit var username: String

    private val viewModel: ChatViewModel by viewModels()

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch(IO) {
            ktorClient = HttpClient(Android) {
                install(JsonFeature)
                install(Logging)
                install(WebSockets) {
                    Log.d(TAG, "WebSocket Connection opened")

                }
            }
            ktorClient.ws(
                method = HttpMethod.Get,
                host = "localhost",
                port = 4444,
                path = "/"
            ) {
                send(Frame.Text("Hello, Text Frame"))
                val messageOutputRoutine = launch { outputMessages(viewModel.allMessages.value) }
                val userInputRoutine = launch { inputMessages(viewModel.message.value) }

                userInputRoutine.join()
                messageOutputRoutine.cancelAndJoin()
            }
        }

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
        ktorClient.close()
        Log.d(TAG, "WebSocket Connection closed")
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

suspend fun DefaultClientWebSocketSession.outputMessages(value: MutableList<ChatMessage>?) {
    try {
        for (message in incoming) {
            message as? Frame.Text ?: continue
            val receivedText = message.readText()
            Log.d(TAG, "output: $receivedText")

        }
    } catch (e: Exception) {
        Log.e(TAG,"Error while receiving")
    }
}

suspend fun DefaultClientWebSocketSession.inputMessages(value: ChatMessage?) {
    while (true) {
        val message = readLine() ?: ""
        if (message.equals("exit", true)) return
        try {
            send(message)
            Log.d(TAG, "Input: $message")

        } catch (e: java.lang.Exception) {
            Log.e(TAG,"Error while sending")
            return
        }
    }
}
