package com.lid.chatapp.presentation

import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MessageCard(message: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Text(message)
    }
}