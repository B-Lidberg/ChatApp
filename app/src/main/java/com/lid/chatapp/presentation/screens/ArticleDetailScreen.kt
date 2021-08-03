package com.lid.chatapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ArticleDetailScreen(articleId: Int, toHomeScreen: () -> Boolean) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Text("Tadah!", style = MaterialTheme.typography.h3)
        Spacer(modifier = Modifier.padding(vertical = 24.dp))
        Button(onClick = { toHomeScreen() }) {
            Text("navigate back to home...")
        }
    }
}