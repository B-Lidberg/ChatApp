package com.lid.chatapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.gson.Gson
import com.lid.chatapp.presentation.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

val gson: Gson = Gson()

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            MainScreen()
        }
    }
}
