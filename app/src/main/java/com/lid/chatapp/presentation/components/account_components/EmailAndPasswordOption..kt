package com.lid.chatapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.SignInButton
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
    var confirmedPassword by remember { mutableStateOf("") }

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
            SignInButton(
                username = userEmail,
                loginState = loginState,
                onClick = { login(userEmail, userPassword) },
                enabled = userEmail.isNotEmpty() &&
                        userPassword.isNotEmpty(),
                scope = scope,
                scaffoldState = scaffoldState
            )
            TextButton(onClick = { loginState = LoginState.REGISTER }) {
                Text("Not Registered? Click here!")
            }
        }
        LoginState.REGISTER -> {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = confirmedPassword,
                onValueChange = { confirmedPassword = it },
                label = { Text("Confirm Password") },
            )
            SignInButton(
                username = userEmail,
                loginState = loginState,
                onClick = { register(userEmail, userPassword) },
                enabled = userEmail.isNotEmpty() &&
                          userPassword.isNotEmpty() &&
                          userPassword == confirmedPassword,
                scope = scope,
                scaffoldState = scaffoldState
            )
            TextButton(onClick = { loginState = LoginState.LOGIN }) {
                Text("Already Registered? Click here!")
            }
        }
    }
}

@Composable
private fun SignInButton(
    username: String,
    loginState: LoginState,
    onClick:() -> Unit,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    enabled: Boolean = true,
    ) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = enabled,
        onClick = {
            onClick()
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Logged in as ${Firebase.auth.currentUser?.email ?: username}"
                )
            }

        }
    ) {
        Text(
            when (loginState) {
                LoginState.LOGIN -> "Login"
                LoginState.REGISTER -> "Register"
            }
        )
    }
}