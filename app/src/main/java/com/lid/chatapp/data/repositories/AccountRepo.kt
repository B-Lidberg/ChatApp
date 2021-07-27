package com.lid.chatapp.data.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import com.lid.chatapp.UserData
import com.lid.chatapp.util.Constants.NO_PASSWORD
import com.lid.chatapp.util.Constants.NO_USERNAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

class AccountRepo @Inject constructor(
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

    suspend fun setUserData(username: String, password: String = NO_PASSWORD) {
        userDataStore.updateData { currentUserData ->
            val currentUsername = currentUserData.username ?: NO_USERNAME
            val currentPassword = currentUserData.password ?: NO_PASSWORD
            val newUsername =
                if (currentUsername != NO_USERNAME && currentPassword != NO_PASSWORD) {
                    username
                } else {
                    currentUsername
                }
            val newPassword =
                if (currentUsername != NO_USERNAME && currentPassword != NO_PASSWORD) {
                    password
                } else {
                    currentPassword
                }
            currentUserData
                .toBuilder()
                .setUsername(newUsername)
                .setPassword(newPassword)
                .build()
        }
    }

    suspend fun clearUserData() {
        userDataStore.updateData { currentUserData ->
            currentUserData
                .toBuilder()
                .clear()
                .build()
        }
    }

}