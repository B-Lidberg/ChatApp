package com.lid.chatapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (message.user == username) Arrangement.End else Arrangement.Start
            ) {
                MessageCard(
                    messageText = message.content,
                    messageUser = message.user!!,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    backgroundColor = if (message.user == username) Color.Green else Color.Cyan
                )
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
            }
        }
    }
}