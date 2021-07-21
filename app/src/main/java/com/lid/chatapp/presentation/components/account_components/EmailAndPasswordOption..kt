package com.lid.chatapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.chatapp.presentation.screens.account_screens.LoginState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EmailAndPasswordOption(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    login: (email: String, password: String) -> Unit,
    register: (email: String, password: String) -> Unit
) {
    var loginState by remember { mutableStateOf(LoginState.LOGIN) }

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = userEmail,
        onValueChange = { userEmail = it },
        label = { Text("Email") },
    )
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = userPassword,
        onValueChange = { userPassword = it },
        label = { Text("Password") },
    )
    when (loginState) {
        LoginState.LOGIN -> {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = userEmail.isNotEmpty() && userPassword.isNotEmpty(),
                onClick = {
                    login(
                        userEmail.trim(),
                        userPassword.trim()
                    )
                    if (Firebase.auth.currentUser != null) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Logged in as ${Firebase.auth.currentUser!!.email}"
                            )
                        }
                    }
                }
            ) {
                Text("Login")
            }
            TextButton(onClick = { loginState = LoginState.REGISTER }) {
                Text("Not Registered? Click here!")
            }
        }
        LoginState.REGISTER -> {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = userEmail.isNotEmpty() && userPassword.isNotEmpty(),
                onClick = {
                    register(
                        userEmail.trim(),
                        userPassword.trim()
                    )
                    if (Firebase.auth.currentUser != null) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Logged in as ${Firebase.auth.currentUser!!.email}"
                            )
                        }
                    }
                }
            ) {
                Text("Register")
            }
            TextButton(onClick = { loginState = LoginState.LOGIN }) {
                Text("Already Registered? Click here!")
            }
        }
    }
}