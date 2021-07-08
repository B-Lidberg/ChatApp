package com.lid.chatapp.di

import android.content.Context
import androidx.room.Room
import com.lid.chatapp.ChatApplication
import com.lid.chatapp.data.local.ChatDao
import com.lid.chatapp.data.local.ChatDatabase
import com.lid.chatapp.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNoteDao(chatDatabase: ChatDatabase): ChatDao  {
        return chatDatabase.chatDao()
    }


    @Provides
    @Singleton
    fun providesChatDatabase(@ApplicationContext context: Context): ChatDatabase {
        return Room.databaseBuilder(
            context,
            ChatDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesApplication(
        @ApplicationContext app: Context
    ): ChatApplication {
        return app as ChatApplication
    }
}