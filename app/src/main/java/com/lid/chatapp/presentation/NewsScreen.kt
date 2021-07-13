package com.lid.chatapp.presentation

import androidx.compose.runtime.Composable
import com.dfl.newsapi.NewsApiRepository
import com.dfl.newsapi.enums.Country
import com.lid.chatapp.presentation.viewmodels.NewsViewModel

@Composable
fun NewsScreen(viewModel: NewsViewModel) {
    val newsApiRepository = NewsApiRepository("myApiKey")
}