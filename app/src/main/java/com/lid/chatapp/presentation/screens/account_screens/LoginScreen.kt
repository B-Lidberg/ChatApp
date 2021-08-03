package com.lid.chatapp.presentation.screens.account_screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.chatapp.presentation.components.account_components.EmailAndPasswordOption
import com.lid.chatapp.presentation.components.account_components.GoogleSignInButton
import com.lid.chatapp.presentation.components.account_components.GuestLoginOption
import com.lid.chatapp.util.LoadingState
import com.lid.chatapp.viewmodels.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class LoginState {
    LOGIN,
    REGISTER
}

@Composable
fun LoginScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: LoginViewModel = hiltViewModel()
) {
    val username by viewModel.currentUsername.observeAsState(
        initial = viewModel.currentUsername.value ?: "")
    val scope = rememberCoroutineScope()

    val loginState by viewModel.loadingState.collectAsState(LoadingState.IDLE)

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp, start = 24.dp, end = 24.dp)
                .verticalScroll(state = ScrollState(0)),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Login Screen",
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )
            EmailAndPasswordOption(
                login = { email, password -> viewModel.signInWithEmailAndPassword(email, password) },
                register = { email, password -> viewModel.registerWithEmailAndPassword(email, password) },
                displaySnackbar = {
                    scope.launch {
                        delay(750)
                        scaffoldState.snackbarHostState.showSnackbar(
                            if (Firebase.auth.currentUser?.email != null) {
                                "Welcome $it!"
                            } else {
                                "Invalid credentials"
                            }
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