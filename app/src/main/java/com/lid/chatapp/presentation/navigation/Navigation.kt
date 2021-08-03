package com.lid.chatapp.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.lid.chatapp.presentation.BookmarkScreen
import com.lid.chatapp.presentation.screens.ArticleDetailScreen
import com.lid.chatapp.presentation.screens.ChatScreen
import com.lid.chatapp.presentation.screens.account_screens.LoginScreen
import com.lid.chatapp.presentation.screens.NewsScreen

@ExperimentalMaterialApi
@ExperimentalAnimatedInsets
@ExperimentalComposeUiApi
@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {
    NavHost(
        navController = navController,
        startDestination = NavScreen.Home.BottomNavItem.BreakingNews.route
    ) {
//        TODO("ISSUE #5: Implement navigation between subroutes in each route")
        composable(NavScreen.Home.BottomNavItem.BreakingNews.route) {
            NewsScreen( { navController.navigate("${NavScreen.ArticleDetail.route}/$it") } )
        }
        composable(NavScreen.Home.BottomNavItem.Account.route) {
            LoginScreen(scaffoldState)
        }
        composable(NavScreen.Home.BottomNavItem.Chat.route) {
            ChatScreen()
        }
        composable(NavScreen.Home.BottomNavItem.Bookmarked.route) {
            BookmarkScreen()
        }

        composable(
            route = NavScreen.ArticleDetail.routeWithArgument,
            arguments = listOf(
                navArgument(NavScreen.ArticleDetail.argument0) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val articleId =
                backStackEntry.arguments?.getInt(NavScreen.ArticleDetail.argument0) ?: return@composable
            ArticleDetailScreen(articleId) {
                navController.popBackStack(NavScreen.Home.route, false) }
        }
    }
}
