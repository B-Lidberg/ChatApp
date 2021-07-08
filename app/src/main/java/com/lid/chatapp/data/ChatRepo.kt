package com.lid.chatapp.data

import com.lid.chatapp.data.local.ChatDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepo @Inject constructor(
    private val chatDao: ChatDao
) {
    suspend fun clearMessages() {
        chatDao.deleteAllMessages()
    }

    fun getAllMessages(): Flow<List<ChatMessage>> {
        return chatDao.getAllMessages()
    }

    suspend fun insertMessage(message: ChatMessage) {
        chatDao.insertMessage(message)
    }
}

