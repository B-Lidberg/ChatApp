package com.lid.chatapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberImeNestedScrollConnection
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.lid.chatapp.data.model.ChatMessage

@ExperimentalAnimatedInsets
@Composable
fun ChatBox(messages: List<ChatMessage>, username: String, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .nestedScroll(connection = rememberImeNestedScrollConnection()),
        reverseLayout = true,

    ) {
        items(items = messages.reversed()) { message ->
            val isUserMessage = message.user == username
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = if (isUserMessage) Arrangement.End else Arrangement.Start
            ) {
                MessageCard(
                    message = message,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    isUserMessage = isUserMessage,
                )
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
            }
        }
    }
}

@ExperimentalAnimatedInsets
@Preview(showBackground = true)
@Composable
fun ChatBoxAsBryanPreview() {
    val list = listOf<ChatMessage>(
        ChatMessage("Hey!", "Stranger"),
        ChatMessage("Hey There!", "Guest"),
        ChatMessage("Test some stuff", "Stranger"),
        ChatMessage("It's me!", "Bryan"),
        ChatMessage("Some longer message to test out. Nothing too crazy! Some longer message to test out. Nothing too crazy! Some longer message to test out. Nothing too crazy! ", "Coffee"),
    )
    val username = "Bryan"
    ChatBox(list, username)
}

@ExperimentalAnimatedInsets
@Preview(showBackground = true)
@Composable
fun ChatBoxAsStrangerPreview() {
    val list = listOf<ChatMessage>(
        ChatMessage("Hey!", "Stranger"),
        ChatMessage("Hey There!", "Guest"),
        ChatMessage("Test some stuff", "Stranger"),
        ChatMessage("It's me!", "Bryan"),
        ChatMessage("Some longer message to test out. Nothing too crazy! Some longer message to test out. Nothing too crazy! Some longer message to test out. Nothing too crazy! ", "Coffee"),
    )
    val username = "Stranger"
    ChatBox(list, username)
}

@ExperimentalAnimatedInsets
@Preview(showBackground = true)
@Composable
fun ChatBoxAsCoffeePreview() {
    val list = listOf<ChatMessage>(
        ChatMessage("Hey!", "Stranger"),
        ChatMessage("Hey There!", "Guest"),
        ChatMessage("Test some stuff", "Stranger"),
        ChatMessage("It's me!", "Bryan"),
        ChatMessage("Some longer message to test out. Nothing too crazy! Some longer message to test out. Nothing too crazy! Some longer message to test out. Nothing too crazy! ", "Coffee"),
    )
    val username = "Coffee"
    ChatBox(list, username)
}