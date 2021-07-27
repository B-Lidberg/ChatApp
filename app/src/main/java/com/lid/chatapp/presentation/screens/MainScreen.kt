package com.lid.chatapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.*
import com.lid.chatapp.presentation.navigation.Navigation
import com.lid.chatapp.presentation.navigation.BottomNavigationBar
import com.lid.chatapp.presentation.components.ChatAppTopBar
import com.lid.chatapp.presentation.ui.theme.ChatAppTheme

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

@ExperimentalAnimatedInsets
@ExperimentalComposeUiApi
@Composable
fun ChatAppFrame(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ChatAppTopBar(
                contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.statusBars),
            )
         },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
    ) { contentPadding ->
        Box(Modifier.padding(contentPadding)) {
            Navigation(navController)
        }

    }
}