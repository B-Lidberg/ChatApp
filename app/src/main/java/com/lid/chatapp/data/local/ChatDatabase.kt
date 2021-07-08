package com.lid.chatapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lid.chatapp.data.ChatMessage

@Database(
    version = 1,
    entities = [ChatMessage::class]
)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao

}