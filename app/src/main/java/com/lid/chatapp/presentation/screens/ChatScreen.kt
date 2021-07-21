package com.lid.chatapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lid.chatapp.presentation.components.ChatBox
import com.lid.chatapp.presentation.viewmodels.ChatViewModel


@Composable
fun ChatScreen(vm: ChatViewModel = hiltViewModel()) {
    val currentMessage by vm.messageText.observeAsState("")
    val messageList by vm.allMessages.observeAsState()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            ClearChatButton { vm.clearChatHistory() }
            ChatBox(messageList ?: emptyList(), modifier = Modifier.weight(1f))
            MessageBox(
                message = currentMessage,
                changeMessage = { vm.onMessageTextChange(it) },
                sendMessage = { vm.sendMessage(it) }
            )
        }
    }

@Composable
fun MessageBox(
    message: String,
    changeMessage: (String) -> Unit,
    sendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = message,
            onValueChange = { changeMessage(it) },
            modifier = Modifier
                .padding(16.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            )
        )
        PostMessageButton { sendMessage(message) }
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
fun ClearChatButton(clearChatHistory: () -> Unit) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { clearChatHistory() }
    ) {
        Text("Clear Chat History")
    }
}
