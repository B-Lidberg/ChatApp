package com.lid.chatapp.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lid.chatapp.data.model.ChatMessage

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
                modifier = Modifier.padding(horizontal = 8.dp),
                backgroundColor = Color.Cyan
            )
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
        }
    }
}