package com.lid.chatapp.presentation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lid.chatapp.data.model.ChatMessage
import com.lid.chatapp.presentation.viewmodels.ChatViewModel


@Composable
fun ChatScreen(vm: ChatViewModel = hiltViewModel()) {
    val currentMessage by vm.messageText.observeAsState("")
    val messageList by vm.allMessages.observeAsState()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { vm.clearChatHistory() }
            ) {
                Text("Clear Chat History")
            }
            ChatBox(messageList ?: emptyList(), modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = currentMessage,
                    onValueChange = { vm.onMessageTextChange(it) },
                    modifier = Modifier
                        .padding(16.dp),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    )
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
fun ChatBox(messages: List<ChatMessage>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
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