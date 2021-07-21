package com.lid.chatapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lid.chatapp.presentation.BookmarkScreen
import com.lid.chatapp.presentation.screens.ChatScreen
import com.lid.chatapp.presentation.screens.account_screens.LoginScreen
import com.lid.chatapp.presentation.screens.NewsScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.BreakingNews.route
    ) {

        composable(NavigationItem.BreakingNews.route) {
            NewsScreen()
        }
        composable(NavigationItem.Account.route) {
            LoginScreen()
        }
        composable(NavigationItem.Chat.route) {
            ChatScreen()
        }
        composable(NavigationItem.Bookmarked.route) {
            BookmarkScreen()
        }
    }
}
