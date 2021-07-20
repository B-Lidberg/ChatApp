package com.lid.chatapp.presentation

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lid.chatapp.util.LoadingState
import com.lid.chatapp.R
import com.lid.chatapp.presentation.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

enum class LoginState {
    LOGIN,
    REGISTER
}


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    signIn: () -> Unit) {

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var tempUser by remember { mutableStateOf("") }

    var loginState by remember { mutableStateOf(LoginState.LOGIN) }
    val loadingState by viewModel.loadingState.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                viewModel.signWithCredential(credential)
                signIn()
            } catch (e: ApiException) {
                Log.w("TAG", "Google sign in failed", e)
            }
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
                        viewModel.signInWithEmailAndPassword(
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
                        viewModel.registerWithEmailAndPassword(
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
        Spacer(modifier = Modifier.height(18.dp))

        val context = LocalContext.current
        val token = stringResource(R.string.default_web_client_id)

        OutlinedButton(
            border = ButtonDefaults.outlinedBorder.copy(width = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            },
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    tint = Color.Unspecified,
                    painter = painterResource(id = R.drawable.googleg_standard_color_18),
                    contentDescription = null
                )
                Text(
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onSurface,
                    text = "Google"
                )
                Icon(
                    tint = Color.Transparent,
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = null
                )
            }
        }
        Button(onClick = { viewModel.signOut() }) {
            Text("Sign Out")
        }

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
            onClick = {
                viewModel.signInAsGuest(tempUser)
            }
        ) {
            Text("Login as guest")
        }

        when (loadingState.status) {
            LoadingState.Status.SUCCESS -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Successfully logged in! ${Firebase.auth.currentUser?.email ?: ":)"}"
                    )
                }
            }
            LoadingState.Status.FAILED -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = loadingState.msg ?: "Error"
                    )
                }
            }
            else -> {
            }
        }
    }
}