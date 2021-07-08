package com.lid.chatapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lid.chatapp.presentation.ChatViewModel
import com.lid.chatapp.presentation.LoginScreen
import com.lid.chatapp.presentation.LoginViewModel

//object MainDestinations {
//    const val LOGIN = "login"
//    const val CHAT = "chat"
//}
//
//@Composable
//fun Navigation() {
//
//    val navController = rememberNavController()
//    val actions = remember(navController) { MainActions(navController) }
//
//    NavHost(navController = navController, startDestination = LOGIN) {
//
//        composable(LOGIN) {
//
//            val loginVm = hiltViewModel<LoginViewModel>()
//            LoginScreen(loginVm, actions.chatScreen)
//        }
//
//        composable(CHAT) {
//            val chatVm = hiltViewModel<ChatViewModel>()
//            ChatScreen(chatVm, actions.loginScreen)
//        }
//
//    }
//}
//
//class MainActions(navController: NavController) {
//
//    val loginScreen: () -> Unit = {
//        navController.navigate(LOGIN) {
//            popUpTo(0) { inclusive = true }
//        }
//    }
//
//    val chatScreen: () -> Unit = {
//        navController.navigate(CHAT) {
//            popUpTo(0) { inclusive = true }
//        }
//    }
//}