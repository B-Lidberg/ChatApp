package com.lid.chatapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BookmarkScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookmarkContent()
    }
    
}

@Composable
fun BookmarkContent() {
    Text("Bookmark Screen Content", style = MaterialTheme.typography.h5)
}

@Preview(showBackground = true)
@Composable
fun BookmarkScreenPreview() {
    BookmarkScreen()
}