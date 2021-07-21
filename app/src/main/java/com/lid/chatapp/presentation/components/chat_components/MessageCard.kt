package com.lid.chatapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MessageCard(
    message: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface
) {
    Card(modifier = modifier, backgroundColor = backgroundColor) {
        Text(
            text = message,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
    }
}