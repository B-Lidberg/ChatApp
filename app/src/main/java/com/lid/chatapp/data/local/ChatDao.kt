package com.lid.chatapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lid.chatapp.data.model.ChatMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Query("SELECT * FROM message_table")
    fun getAllMessages(): Flow<List<ChatMessage>>

    @Insert
    suspend fun insertMessage(message: ChatMessage)

    @Query("DELETE FROM message_table")
    suspend fun deleteAllMessages()

}