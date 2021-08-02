package com.lid.chatapp.presentation.screens.account_screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.chatapp.presentation.components.account_components.EmailAndPasswordOption
import com.lid.chatapp.presentation.components.account_components.GoogleSignInButton
import com.lid.chatapp.presentation.components.account_components.GuestLoginOption
import com.lid.chatapp.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

enum class LoginState {
    LOGIN,
    REGISTER
}

@Composable
fun LoginScreen(scaffoldState: ScaffoldState, viewModel: LoginViewModel = hiltViewModel()) {
    val username by viewModel.currentUsername.observeAsState(viewModel.currentUsername.value ?: "")
    val scope = rememberCoroutineScope()

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            EmailAndPasswordOption(
                login = { email, password -> viewModel.signInWithEmailAndPassword(email, password) },
                register = { email, password -> viewModel.registerWithEmailAndPassword(email, password) },
                displaySnackbar = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            "Welcome $it!"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(18.dp))

            GoogleSignInButton(
                signInWithCredential = { viewModel.signWithCredential(it) },
                displaySnackbar = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            "Welcome $it!"
                        )
                    }
                }
            )
            Button(
                onClick = {
                    viewModel.signOut()
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            if (username.isNullOrEmpty()) {
                                "Goodbye ${Firebase.auth.currentUser?.email ?: "friend"}!"
                            } else {
                                "Goodbye $username!"
                            }
                        )
                    }
                },

                ) {
                Text("Sign Out")
            }

            GuestLoginOption(
                signIn = { viewModel.signInAsGuest(it) },
                displaySnackbar = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            "Welcome Guest: $it!"
                        )
                    }
                }
            )
            if (!username.isNullOrEmpty()) {
                Text("Welcome $username!")
            } else {
                Text("Try signing in as a guest")
            }
        }
    }
}