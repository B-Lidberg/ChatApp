package com.lid.chatapp.data.local.Chat

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lid.chatapp.data.model.ChatMessage

@Database(
    version = 1,
    entities = [ChatMessage::class]
)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao

}