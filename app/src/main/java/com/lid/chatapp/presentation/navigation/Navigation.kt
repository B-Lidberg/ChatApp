package com.lid.chatapp.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
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
    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = NavScreen.Home.BottomNavItem.News.route
    ) {
//        TODO("ISSUE #5: Implement navigation between subroutes in each route")

        composable(NavScreen.Home.BottomNavItem.News.route) {
            NewsScreen(actions.toArticleScreen)
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
            route = "articleDetails/{articleUrl}"
        ) { backStackEntry ->
            val articleUrl = backStackEntry.arguments?.getString("articleUrl") ?: return@composable
            ArticleDetailScreen(articleUrl = articleUrl, actions.upPress)
        }
    }
}

class MainActions(navController: NavHostController) {
    val toArticleScreen: (String) -> Unit = { articleUrl: String ->
        navController.createDeepLink().setDestination("articleDetails/$articleUrl")
        navController.navigate("articleDetails/$articleUrl")
    }

    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}


/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
