package com.lid.chatapp.presentation.screens.account_screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lid.chatapp.presentation.components.EmailAndPasswordOption
import com.lid.chatapp.presentation.components.GoogleSignInButton
import com.lid.chatapp.presentation.components.GuestLoginOption
import com.lid.chatapp.viewmodels.LoginViewModel

enum class LoginState {
    LOGIN,
    REGISTER
}

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {

/*
    TODO("ISSUE #3: Add Snackbar for loadingState SUCCESS and FAIL")
    val loadingState by viewModel.loadingState.collectAsState()
*/
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val username by viewModel.currentUsername.observeAsState(viewModel.currentUsername.value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmailAndPasswordOption(
            scope,
            scaffoldState,
            { email, password -> viewModel.signInWithEmailAndPassword(email, password) },
            { email, password -> viewModel.registerWithEmailAndPassword(email, password) },
        )
        Spacer(modifier = Modifier.height(18.dp))

        GoogleSignInButton { viewModel.signWithCredential(it) }

        Button(enabled = !username.isNullOrEmpty(), onClick = { viewModel.signOut() }) {
            Text("Sign Out")
        }

        GuestLoginOption { viewModel.signInAsGuest(it) }

        if (!username.isNullOrEmpty()) {
            Text("Welcome $username!")
        } else {
            Text("Try signing in as a guest")
        }

    }
}