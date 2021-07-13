package com.lid.chatapp.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.lid.chatapp.data.model.Article
import com.lid.chatapp.presentation.viewmodels.NewsViewModel
import com.lid.chatapp.util.Constants.TAG

@Composable
fun NewsScreen(viewModel: NewsViewModel) {

    Scaffold(
        topBar = {
            TopAppBar() {
                Text("News App")
            }
        }
    ) {

        ArticleList(viewModel)
    }
}

@Composable
fun ArticleList(viewModel: NewsViewModel) {

    val articleList by remember { viewModel.newsList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    LazyColumn {
        items(articleList) { article ->
            ArticleEntry(article = article)
        }
    }
}

@Composable
fun ArticleEntry(article: Article, modifier: Modifier = Modifier) {
    if (!article.urlToImage.isNullOrEmpty()) {
    val painter = rememberCoilPainter(article.urlToImage)
    Card(modifier = modifier.padding(16.dp)) {
        Column {
            Text(text = article.source.name)
            Image(
                painter = painter,
                contentDescription = "article image: ${article.id}",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.2f)
            )
            Log.d(TAG, article.urlToImage)

        }
    }
    }
}