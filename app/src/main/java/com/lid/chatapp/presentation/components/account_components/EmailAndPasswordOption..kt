package com.lid.chatapp.presentation.components.account_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lid.chatapp.presentation.screens.account_screens.LoginState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait

@Composable
fun EmailAndPasswordOption(
    login: (email: String, password: String) -> Unit,
    register: (email: String, password: String) -> Unit,
    displaySnackbar: (String) -> Unit
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
                loginState = loginState,
                onClick = {
                    login(userEmail, userPassword)
                    displaySnackbar(userEmail)
                },
                enabled = userEmail.isNotEmpty() &&
                        userPassword.isNotEmpty(),
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
                loginState = loginState,
                onClick = {
                    register(userEmail, userPassword)
                    displaySnackbar(userEmail)
                },
                enabled = userEmail.isNotEmpty() &&
                          userPassword.isNotEmpty() &&
                          userPassword == confirmedPassword,
            )
            TextButton(onClick = { loginState = LoginState.LOGIN }) {
                Text("Already Registered? Click here!")
            }
        }
    }
}

@Composable
private fun SignInButton(
    loginState: LoginState,
    onClick:() -> Unit,
    enabled: Boolean = true,
    ) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Text(
            when (loginState) {
                LoginState.LOGIN -> "Login"
                LoginState.REGISTER -> "Register"
            }
        )
    }
}