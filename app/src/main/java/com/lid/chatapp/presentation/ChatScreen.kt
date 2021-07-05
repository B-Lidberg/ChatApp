package com.lid.chatapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.chatapp.presentation.ChatViewModel


@Composable
fun ChatScreen(vm: ChatViewModel = viewModel(), signOut: () -> Unit) {

    val currentMessage = vm.message.observeAsState("")
    val allMessages = vm.allMessages.observeAsState(emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TopAppBar(
                    backgroundColor = Color.White,
                    elevation = 1.dp,
                    title = {
                        Text(Firebase.auth.currentUser?.email ?: "")
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
                            signOut()
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
            PostMessageButton(currentMessage.value) {
                vm.sendMessage(currentMessage.value)
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            LazyColumn() {
                items(items = allMessages.value) { message ->
                    Text(message)
                }
            }
            OutlinedTextField(
                value = currentMessage.value,
                onValueChange = { vm.onMessageChange(it) },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun PostMessageButton(message: String, sendMessage: (String) -> Unit) {
    FloatingActionButton(
        onClick = { sendMessage(message) },
    ) {
        Text("Send")
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen() {
        
    }
}