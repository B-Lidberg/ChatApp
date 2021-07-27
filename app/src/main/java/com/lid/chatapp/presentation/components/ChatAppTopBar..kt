package com.lid.chatapp.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChatAppTopBar(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    TopAppBar(
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        Text(
            "News Chatting App",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}