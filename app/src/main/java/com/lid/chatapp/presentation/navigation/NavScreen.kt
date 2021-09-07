package com.lid.chatapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Announcement
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavScreen(var route: String) {

    object Home : NavScreen("home") {
        sealed class BottomNavItem(var route: String, var icon: ImageVector, var title: String) {
            object News : BottomNavItem("articles", Icons.Default.Announcement, "News")
            object Chat : BottomNavItem("chat", Icons.Default.ChatBubble, "Chat")
            object Bookmarked : BottomNavItem("bookmarked", Icons.Default.Bookmarks, "Bookmarked")
            object Account : BottomNavItem("account", Icons.Default.Person, "Account")
        }
    }
    object ArticleDetail : NavScreen("articleDetails") {
        const val routeWithArgument: String = "articleDetails/{articleUrl}"
        const val argument0: String = "articleUrl"
    }
}
