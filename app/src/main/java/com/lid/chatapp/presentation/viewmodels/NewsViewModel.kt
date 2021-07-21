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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepo: NewsRepo
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    val newsList = mutableStateOf<List<Article>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadArticlesPaginated()
    }

    private fun loadArticlesPaginated() {
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
}