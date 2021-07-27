package com.lid.chatapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.LocalWindowInsets
import com.lid.chatapp.presentation.components.ChatBox
import com.lid.chatapp.viewmodels.ChatViewModel


@ExperimentalAnimatedInsets
@ExperimentalComposeUiApi
@Composable
fun ChatScreen(vm: ChatViewModel = hiltViewModel()) {
    val currentMessage by vm.messageText.observeAsState("")
    val messageList by vm.allMessages.observeAsState()
    val username by vm.currentUsername.observeAsState("")



    Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            ClearChatButton { vm.clearChatHistory() }
            Text(username ?: "NO_USERNAME")
            ChatBox(messageList ?: emptyList(), username = username,  modifier = Modifier.weight(1f))
            MessageBox(
                message = currentMessage,
                changeMessage = { vm.onMessageTextChange(it) },
                sendMessage = { vm.sendMessage(it) },
            )

        }
    }

@ExperimentalComposeUiApi
@Composable
fun MessageBox(
    message: String,
    changeMessage: (String) -> Unit,
    sendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardVisible = LocalWindowInsets.current.ime.isVisible
    val active = remember { mutableStateOf(keyboardVisible) }
    
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val focusRequester = FocusRequester()

        TextField(
            value = message,
            onValueChange = { changeMessage(it) },
//                .navigationBarsWithImePadding(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.onBackground
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .weight(1f)
                .border(
                    BorderStroke(2.dp, color = MaterialTheme.colors.onBackground),
                    shape = CircleShape
                )
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    active.value = focusState.isFocused

                }
        )

        PostMessageButton( { sendMessage(message) }) { keyboardController?.hide() }
    }
}

@Composable
fun PostMessageButton(sendMessage: () -> Unit, hideKeyboard: () -> Unit?) {
    IconButton(
        onClick = {
            sendMessage()
            hideKeyboard()
            Log.d("TAG", "send message clicked")
        },
        modifier = Modifier.padding(2.dp)

    ) {
        Icon(
            Icons.Default.Send,
            contentDescription = "send message",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier.size(45.dp)

        )

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

@ExperimentalComposeUiApi
@Preview
@Composable
fun MessageBoxPreview() {
    MessageBox(message = "", changeMessage = { }, sendMessage = {})
}
