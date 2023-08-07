package com.lahsuak.apps.instagram.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lahsuak.apps.instagram.R
import com.lahsuak.apps.instagram.models.Tweet
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme
import com.lahsuak.apps.instagram.ui.theme.LIGHT_BLUE
import com.lahsuak.apps.instagram.util.DateUtil.getDateTime
import com.lahsuak.apps.instagram.util.DemoData

@Composable
fun TweetItem(
    tweet: Tweet,
    onCommentClick: () -> Unit,
    onItemClick: () -> Unit,
) {
    var isLiked by rememberSaveable {
        mutableStateOf(false)
    }
    var isRetweeted by rememberSaveable {
        mutableStateOf(false)
    }
    var isBookmarked by rememberSaveable {
        mutableStateOf(false)
    }
    var likes by rememberSaveable {
        mutableIntStateOf(tweet.likeCount)
    }
    var retweets by rememberSaveable {
        mutableIntStateOf(tweet.retweetCount)
    }
    var bookmarks by rememberSaveable {
        mutableIntStateOf(tweet.bookmarkCount)
    }
    val user = DemoData.demoUser
    Row(
        Modifier
            .fillMaxWidth(1f)
            .padding(8.dp)
            .clickable {
                onItemClick()
            }
    ) {
        CircularImage(imageUrl = user.profileImage, imageSize = 45.dp)
        Spacer(Modifier.width(8.dp))
        Column(Modifier.fillMaxWidth(1f)) {
            Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(user.name, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = getDateTime(tweet.timeStamp),
                    textAlign = TextAlign.End,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(tweet.description, fontSize = 16.sp, fontWeight = FontWeight.Light)
            Spacer(Modifier.height(8.dp))
            TweetActionRow(isLiked, isRetweeted, isBookmarked,
                likes,
                retweets,
                bookmarks,
                onLikeClicked = {
                    if (it)
                        likes++
                    else
                        likes--
                    isLiked = it
                }, onRetweetClicked = {
                    if (it)
                        retweets++
                    else
                        retweets--
                    isRetweeted = it
                }, onBookmarkClicked = {
                    if (it)
                        bookmarks++
                    else
                        bookmarks--
                    isBookmarked = it
                }) {
                onCommentClick()
            }
        }
    }
}

@Composable
fun NumberIcon(
    count: Int,
    initialState: Boolean,
    enableIcon: Painter,
    enableTint: Color = MaterialTheme.colorScheme.onBackground,
    disableIcon: Painter,
    disableTint: Color = MaterialTheme.colorScheme.onBackground,
    onClick: (Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ToggleIconButton(
            enableIcon = enableIcon,
            disableIcon = disableIcon,
            enableTint = enableTint,
            initialState = initialState,
            disableTint = disableTint
        ) {
            onClick(it)
        }
        Spacer(modifier = Modifier.width(2.dp))
        Text(count.toString(), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun TweetActionRow(
    isLiked: Boolean,
    isRetweeted: Boolean,
    isBookmarked: Boolean,
    likeCount: Int,
    retweetCount: Int,
    bookmarkCount: Int,
    onLikeClicked: (Boolean) -> Unit,
    onRetweetClicked: (Boolean) -> Unit,
    onBookmarkClicked: (Boolean) -> Unit,
    onCommentClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        IconButton(onClick = {
            onCommentClick()
        }) {
            Icon(
                painterResource(id = R.drawable.ic_comment),
                contentDescription = "Comment"
            )
        }
        NumberIcon(
            count = retweetCount,
            initialState = isRetweeted,
            painterResource(id = R.drawable.ic_retweet),
            LIGHT_BLUE,
            painterResource(R.drawable.ic_retweet)
        ) {
            onRetweetClicked(it)
        }
        NumberIcon(
            count = likeCount,
            initialState = isLiked,
            rememberVectorPainter(image = Icons.Default.Favorite),
            Color.Red,
            rememberVectorPainter(image = Icons.Default.FavoriteBorder),
        ) {
            onLikeClicked(it)
        }
        NumberIcon(
            count = bookmarkCount,
            initialState = isBookmarked,
            enableIcon = painterResource(id = R.drawable.ic_bookmark),
            disableIcon = painterResource(id = R.drawable.ic_bookmark_border)
        ) {
            onBookmarkClicked(it)
        }
        IconButton(onClick = {
            //share tweet link
        }) {
            Icon(
                Icons.Default.Share,
                contentDescription = "Share"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TweetItemPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TweetItem(
                Tweet(
                    description = "Incorporate and convert Java code into #Kotlin using #Android Studio, and learn Kotlin language conventions along the way. You’ll also learn how to write Kotlin code to make it callable from Java code.",
                    userId = DemoData.demoUser.id,
                    comments = listOf("very nice"),
                    likeCount = 203,
                    retweetCount = 50,
                    bookmarkCount = 135,
                    imageUrl = DemoData.urls.subList(0, 3)
                ),
                onCommentClick = {}
            ) {
            }
        }
    }
}