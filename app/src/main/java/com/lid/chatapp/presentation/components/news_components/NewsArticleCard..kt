package com.lid.chatapp.presentation.components.news_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.lid.chatapp.data.model.Article

@Composable
fun NewsArticleCard(article: Article, modifier: Modifier = Modifier) {
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
            ArticleOptions()
        }
    }
}

@Composable
fun ArticleOptions() {
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