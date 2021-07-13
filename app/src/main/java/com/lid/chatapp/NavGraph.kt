package com.lid.chatapp

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