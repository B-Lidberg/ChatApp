package com.lid.chatapp.data.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import com.lid.chatapp.UserData
import com.lid.chatapp.data.local.Chat.ChatDao
import com.lid.chatapp.data.model.ChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

class ChatRepo @Inject constructor(
    private val chatDao: ChatDao,
    private val userDataStore: DataStore<UserData>
) {

    val userDataFlow: Flow<UserData> = userDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e("Error reading user data.", exception.localizedMessage ?: "IOException")
            } else {
                throw exception
            }
        }

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

