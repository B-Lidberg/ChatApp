package com.lid.chatapp

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lid.chatapp.presentation.BookmarkScreen
import com.lid.chatapp.presentation.LoginScreen
import com.lid.chatapp.presentation.NewsScreen
import com.lid.chatapp.presentation.components.NavigationItem
import com.lid.chatapp.presentation.viewmodels.ChatViewModel
import com.lid.chatapp.presentation.viewmodels.LoginViewModel
import com.lid.chatapp.presentation.viewmodels.NewsViewModel

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationItem.BreakingNews.route) {

        composable(NavigationItem.BreakingNews.route) {
            val newsVm = hiltViewModel<NewsViewModel>()
            NewsScreen(newsVm)
        }
        composable(NavigationItem.Profile.route) {
            val loginVm = hiltViewModel<LoginViewModel>()
            LoginScreen() { TODO("add sign-in method") }
        }
        composable(NavigationItem.Chat.route) {
            val chatVm = hiltViewModel<ChatViewModel>()
            ChatScreen(chatVm)
        }
        composable(NavigationItem.Bookmarked.route) {
            BookmarkScreen()
        }
    }

}