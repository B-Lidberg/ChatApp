package com.lid.chatapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Announcement
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object BreakingNews : NavigationItem("home", Icons.Default.Announcement, "Home")
    object Chat : NavigationItem("chat", Icons.Default.ChatBubble, "Chat")
    object Bookmarked : NavigationItem("bookmarked", Icons.Default.Bookmarks, "Bookmarked")
    object Account : NavigationItem("account", Icons.Default.Person, "Account")
}
