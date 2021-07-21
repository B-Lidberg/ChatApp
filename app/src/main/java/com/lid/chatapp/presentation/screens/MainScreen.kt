package com.lid.chatapp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.*
import com.lid.chatapp.Navigation
import com.lid.chatapp.navigation.BottomNavigationBar
import com.lid.chatapp.ui.theme.ChatAppTheme

@Composable
fun MainScreen() {

    val snackbarHostState = remember { SnackbarHostState() }


    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        ChatAppTheme {
            Surface(color = MaterialTheme.colors.background) {
                val navController = rememberNavController()

                Scaffold(
                    scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
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
        }
    }
}