package com.lid.chatapp.data.repositories

import com.lid.chatapp.data.model.NewsResponse
import com.lid.chatapp.data.remote.NewsApi
import com.lid.chatapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class NewsRepo @Inject constructor(
    private val newsApi: NewsApi
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        newsApi.getBreakingNews(countryCode, pageNumber)

}