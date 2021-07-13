package com.lid.chatapp

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.chatapp.data.model.ChatMessage
import com.lid.chatapp.presentation.MessageCard
import com.lid.chatapp.presentation.viewmodels.ChatViewModel
import com.lid.chatapp.util.Constants


@Composable
fun ChatScreen(vm: ChatViewModel = hiltViewModel()) {
    val currentMessage by vm.messageText.observeAsState("")
    val messageList by vm.allMessages.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        ChatBox(messageList ?: emptyList())
        Row {
            OutlinedTextField(
                value = currentMessage,
                onValueChange = { vm.onMessageTextChange(it) },
                modifier = Modifier.padding(16.dp).navigationBarsWithImePadding()
            )
            PostMessageButton { vm.sendMessage(currentMessage) }
        }
    }
}

@Composable
fun PostMessageButton(sendMessage: () -> Unit) {
    Button(
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