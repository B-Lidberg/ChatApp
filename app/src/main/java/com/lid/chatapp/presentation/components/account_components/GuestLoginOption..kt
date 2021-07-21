package com.lid.chatapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GuestLoginOption(signIn: (String) -> Unit) {
    var tempUser by remember { mutableStateOf("") }

    OutlinedTextField(
        value = tempUser,
        onValueChange = { tempUser = it },
        label = { Text("Guest") },
    )
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = tempUser.isNotEmpty(),
        onClick = { signIn(tempUser) }
    ) {
        Text("Login as guest")
    }
}