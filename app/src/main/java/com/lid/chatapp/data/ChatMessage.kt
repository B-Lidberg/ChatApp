package com.lid.chatapp.data

import java.util.*

data class ChatMessage(
    val content: String,
    val user: String? = "guest",
    val timeStamp: Long = Date().time
)

data class InitialData(
    val user: String = "guest"
)

data class SendMessage(
    val content: String,
    val user: String? = "guest",
    val timeStamp: Long = Date().time
)
