package com.lid.chatapp.di

import android.content.Context
import androidx.room.Room
import com.lid.chatapp.ChatApplication
import com.lid.chatapp.data.local.Article.ArticleDao
import com.lid.chatapp.data.local.Article.ArticleDatabase
import com.lid.chatapp.data.local.Chat.ChatDao
import com.lid.chatapp.data.local.Chat.ChatDatabase
import com.lid.chatapp.data.remote.NewsApi
import com.lid.chatapp.data.repositories.NewsRepo
import com.lid.chatapp.util.Constants
import com.lid.chatapp.util.Constants.ARTICLE_DATABASE_NAME
import com.lid.chatapp.util.Constants.CHAT_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesChatDao(chatDatabase: ChatDatabase): ChatDao {
        return chatDatabase.chatDao()
    }


    @Provides
    @Singleton
    fun providesChatDatabase(@ApplicationContext context: Context): ChatDatabase {
        return Room.databaseBuilder(
            context,
            ChatDatabase::class.java,
            CHAT_DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providesArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.articleDao()
    }

    @Provides
    @Singleton
    fun providesArticleDatabase(@ApplicationContext context: Context): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            ARTICLE_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesNewsRepository(
        newsApi: NewsApi
    ) = NewsRepo(newsApi)

    @Provides
    @Singleton
    fun providesNewsApi(
    ): NewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_NEWS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)

    }

    @Provides
    @Singleton
    fun providesApplication(
        @ApplicationContext app: Context
    ): ChatApplication {
        return app as ChatApplication
    }
}