package com.lid.chatapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.*
import com.lid.chatapp.navigation.Navigation
import com.lid.chatapp.navigation.BottomNavigationBar
import com.lid.chatapp.presentation.components.ChatAppTopBar
import com.lid.chatapp.ui.theme.ChatAppTheme

@Composable
fun MainScreen() {

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        ChatAppTheme {
            Surface(color = MaterialTheme.colors.background) {
                ChatAppFrame()
            }
        }
    }
}

@Composable
fun ChatAppFrame(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { ChatAppTopBar(
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.statusBars,
                applyStart = true,
                applyTop = true,
                applyEnd = true,
            )
        ) },
        bottomBar = { BottomNavigationBar(navController) },
    ) { contentPadding ->
        Box(Modifier.padding(contentPadding)) {
            Navigation(navController)
        }

    }
}