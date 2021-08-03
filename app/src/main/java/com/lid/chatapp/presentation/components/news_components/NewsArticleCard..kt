package com.lid.chatapp.presentation.components.news_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.lid.chatapp.data.model.Article

@Preview
@Composable
fun ImagePreview() {
    Card() {
        Image(
            painter = rememberImagePainter("com.android.providers.media.documents/document/image%3A51074"),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(225.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun NewsArticleCard(
    article: Article,
    toDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val painter = rememberImagePainter(article.urlToImage)

    Card(
        modifier = modifier.padding(12.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        onClick = { article.id?.let { toDetails(it) } }
    ) {
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
        horizontalArrangement = Arrangement.End,
    ) {
// TODO("ISSUE #8: Add Share IconButton that sends article to Chatroom")
        Icon(
            imageVector = Icons.Default.BookmarkBorder,
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleOptionsPreview() {
    ArticleOptions()
}