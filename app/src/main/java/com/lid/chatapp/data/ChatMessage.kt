package com.lid.chatapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "message_table")
data class ChatMessage(
    val content: String,
    val user: String? = "guest",
    val timeStamp: Long = Date().time,
    @PrimaryKey(autoGenerate = false)
    val noteId: String = UUID.randomUUID().toString()
)
