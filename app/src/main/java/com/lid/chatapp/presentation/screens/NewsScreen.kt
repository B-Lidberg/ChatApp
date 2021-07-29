package com.lid.chatapp.presentation.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.lid.chatapp.data.model.Article
import com.lid.chatapp.presentation.components.news_components.NewsArticleCard
import com.lid.chatapp.viewmodels.NewsViewModel

@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel()) {
//    val articleList by remember { viewModel.newsList }
    ArticleList(viewModel.newsList.value)
}

@Composable
fun ArticleList(articleList: List<Article>) {

    LazyColumn {
        items(articleList) { article ->
            if (!article.urlToImage.isNullOrEmpty()) {
                NewsArticleCard(article)
            }
        }
    }
}