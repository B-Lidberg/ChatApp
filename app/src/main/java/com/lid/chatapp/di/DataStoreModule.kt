package com.lid.chatapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import com.lid.chatapp.UserData
import com.lid.chatapp.data.datastore.UserDataSerializer
import com.lid.chatapp.util.Constants.USER_DATASTORE_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    fun providesUserDataSerializer() = UserDataSerializer

    @Provides
    @Singleton
    fun providesUserDataStore(
        @ApplicationContext context: Context,
        serializer: UserDataSerializer
    ): DataStore<UserData> =
        DataStoreFactory.create(
            produceFile = { context.dataStoreFile(USER_DATASTORE_FILE_NAME) },
            serializer = serializer
        )
}