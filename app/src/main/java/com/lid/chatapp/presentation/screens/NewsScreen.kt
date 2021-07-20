package com.lid.chatapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.lid.chatapp.data.model.Article
import com.lid.chatapp.presentation.viewmodels.NewsViewModel

@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel()) {
    ArticleList(viewModel)
}

@Composable
fun ChatAppTopBar(contentPadding: PaddingValues) {
    TopAppBar(contentPadding = contentPadding) {
        Text(
            "News Chatting App",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

@Composable
fun NavIcon(icon: ImageVector, text: String, modifier: Modifier = Modifier, onClick:() -> Unit) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .clickable(onClick = onClick)
                .padding(6.dp)
        ) {
            Icon(icon, text)
            Text(text)
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
fun ArticleEntry(
    article: Article,
    modifier: Modifier = Modifier
) {
    if (!article.urlToImage.isNullOrEmpty()) {
        val painter = rememberCoilPainter(article.urlToImage)
        Card(modifier = modifier.padding(4.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painter,
                    contentDescription = "article image: ${article.id}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.h5,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Default.BookmarkBorder,
                        contentDescription = null
                    )
                }
            }
        }
    }
}