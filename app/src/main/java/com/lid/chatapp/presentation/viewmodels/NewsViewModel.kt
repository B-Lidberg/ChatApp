package com.lid.chatapp.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lid.chatapp.data.model.Article
import com.lid.chatapp.data.model.NewsResponse
import com.lid.chatapp.data.repositories.NewsRepo
import com.lid.chatapp.util.Constants.PAGE_SIZE
import com.lid.chatapp.util.Constants.TAG
import com.lid.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepo: NewsRepo
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    val newsList = mutableStateOf<List<Article>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    var cachedNewsList = listOf<Article>()

    init {
        loadArticlesPaginated()
    }

    fun loadArticlesPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = newsRepo.getBreakingNews("us", breakingNewsPage)
            when (result) {
                is Resource.Success -> {
                    Log.d(TAG, "RESULT == SUCCESS: ${result.data}:")
                    endReached.value = breakingNewsPage * PAGE_SIZE >= result.data!!.articles.count()
                    val articleEntries = result.data.articles
                    breakingNewsPage++
                    loadError.value = ""
                    isLoading.value = false
                    newsList.value += articleEntries
                }
                is Resource.Error -> {
                    Log.d(TAG, "RESULT == ERROR: ${result.data}:")

                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }


    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepo.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(response)
    }
//
//    private fun handleNewsResponse(response: Resource<NewsResponse>): Resource<NewsResponse> {
//        if (response.isSuccessful) {
//            response.body()?.let { resultResponse ->
//                return Resource.Success(resultResponse)
//            }
//        }
//        return Resource.Error(response.message())
//    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepo.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(response)
    }

}