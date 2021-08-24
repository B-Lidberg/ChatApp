package com.lid.chatapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.*
import com.lid.chatapp.presentation.navigation.Navigation
import com.lid.chatapp.presentation.navigation.BottomNavigationBar
import com.lid.chatapp.presentation.ui.theme.ChatAppTheme

@ExperimentalMaterialApi
@ExperimentalAnimatedInsets
@ExperimentalComposeUiApi
@Composable
fun MainScreen() {

    ProvideWindowInsets(
        consumeWindowInsets = false,
    ) {
        ChatAppTheme {
            Surface(color = MaterialTheme.colors.background) {
                ChatAppFrame()
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimatedInsets
@ExperimentalComposeUiApi
@Composable
fun ChatAppFrame(
    navController: NavHostController = rememberNavController()
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
    ) { contentPadding ->
        Box(Modifier.padding(contentPadding)) {
            Navigation(navController, scaffoldState)
        }

    }
}

/* Removed Top bar. Not sure if I'll put it back in on any screens
    topBar = {
        ChatAppTopBar(
            contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.statusBars),
        )
     },
 */