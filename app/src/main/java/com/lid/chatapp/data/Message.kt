package com.lid.chatapp.data

import java.util.*

data class Message(
    val content: String,
    val user: String? = "guest",
    val id: String = UUID.randomUUID().toString()
)
