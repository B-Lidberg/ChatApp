package com.lid.chatapp.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
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
        Card(modifier = modifier.padding(4.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = article.title, style = MaterialTheme.typography.h5)
                Image(
                    painter = painter,
                    contentDescription = "article image: ${article.id}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp),
                    contentScale = ContentScale.FillBounds
                )
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp)) {
                    Row(modifier = Modifier.weight(9f)) {
                        Icon(imageVector = Icons.Default.ThumbUp, contentDescription = null)
                        Text("12")
                        Spacer(modifier = Modifier.width(40.dp))
                        Icon(imageVector = Icons.Default.Comment, contentDescription = null)
                        Text("6")

                    }
                    Icon(imageVector = Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.weight(1f))
                }
                Log.d(TAG, article.urlToImage)

            }
        }
    }
}