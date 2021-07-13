package com.lid.chatapp

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.gson.Gson
import com.lid.chatapp.data.model.ChatMessage
import com.lid.chatapp.presentation.MainScreen
import com.lid.chatapp.presentation.viewmodels.ChatViewModel
import com.lid.chatapp.presentation.MessageCard
import com.lid.chatapp.presentation.NewsScreen
import com.lid.chatapp.presentation.viewmodels.NewsViewModel
import com.lid.chatapp.ui.theme.ChatAppTheme
import com.lid.chatapp.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket
import io.socket.client.IO
import io.socket.emitter.Emitter
import kotlinx.coroutines.*
import java.net.URISyntaxException

val gson: Gson = Gson()

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MainScreen()
        }
    }
}
