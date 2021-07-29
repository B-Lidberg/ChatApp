package com.lid.chatapp.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lid.chatapp.data.model.ChatMessage
import com.lid.chatapp.presentation.ui.theme.ChatAppTheme

@Composable
fun MessageCard(
    message: ChatMessage,
    modifier: Modifier = Modifier,
    isUserMessage: Boolean = false,
) {
    Card(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(end = 4.dp)
        ) {
            if (!isUserMessage) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colors.onSurface.copy(alpha = .5f),
                    modifier = Modifier
                        .padding(8.dp)
                        .size(40.dp),
                    content = { }
                )
            }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = if (isUserMessage) {
                        Modifier.padding(start = 32.dp)
                    } else {
                        Modifier.padding(end = 32.dp)
                    }
                ) {
                if (!isUserMessage) {
                    Text(
                        text = "${message.user}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 4.dp),
                        color = MaterialTheme.colors.onBackground
                    )
                }
                Surface(
                    color = (if (isUserMessage) Color.Green else Color.Cyan),
                    shape = RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp, topEnd = 6.dp),
                    modifier = Modifier.defaultMinSize(minWidth = 40.dp)
                ) {
                    Text(
                        text = message.content,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(8.dp),
                        color = Color.Black

                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OtherUserSmallMessagePreview() {
    val message = ChatMessage("Hey", user = "Stranger")
    MessageCard(message = message)
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OtherUserLongMessageDarkPreview() {
    ChatAppTheme() {
        val message = ChatMessage(
            "Hey there! Here's some content..Improve knowledge on Coroutines by implementing numerous server/db calls asynchronously. Practice UI/UX through Material Design",
            user = "Stranger"
        )
        MessageCard(message = message)
    }
}

@Preview
@Composable
fun OtherUserLongMessageLightPreview() {
    val message = ChatMessage("Hey there! Here's some content..Improve knowledge on Coroutines by implementing numerous server/db calls asynchronously. Practice UI/UX through Material Design", user = "Stranger")
    MessageCard(message = message)
}

@Preview
@Composable
fun CurrentUserLongMessageLMPreview() {
    val message = ChatMessage("Hey there! Here's some content..Improve knowledge on Coroutines by implementing numerous server/db calls asynchronously. Practice UI/UX through Material Design", user = "Stranger")
    MessageCard(message = message, isUserMessage = true)
}