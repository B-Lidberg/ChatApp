package com.lid.chatapp.data.repositories

import com.lid.chatapp.data.model.NewsResponse
import com.lid.chatapp.data.remote.NewsApi
import com.lid.chatapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class NewsRepo @Inject constructor(
    private val newsApi: NewsApi
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Resource<NewsResponse> {
        val response = try {
            newsApi.getBreakingNews(countryCode, pageNumber)
        } catch (e: Exception) {
            return Resource.Error("An unknown error has occurred")
        }
        return Resource.Success(response)
    }

    suspend fun searchNews(searchQuery: String, pageNumber: Int): Resource<NewsResponse> {
        val response = try {
            newsApi.searchForNews(searchQuery, pageNumber)
        } catch (e: Exception) {
            return Resource.Error("An unknown error has occurred")
        }
        return Resource.Success(response)
    }
}