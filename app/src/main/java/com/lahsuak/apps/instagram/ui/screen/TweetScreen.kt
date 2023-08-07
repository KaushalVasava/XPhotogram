package com.lahsuak.apps.instagram.ui.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lahsuak.apps.instagram.models.ApiFailure
import com.lahsuak.apps.instagram.models.BaseState
import com.lahsuak.apps.instagram.ui.components.CenterCircularProgressBar
import com.lahsuak.apps.instagram.ui.components.CenterErrorText
import com.lahsuak.apps.instagram.ui.components.CircularImage
import com.lahsuak.apps.instagram.ui.components.NumberText
import com.lahsuak.apps.instagram.ui.components.TweetActionRow
import com.lahsuak.apps.instagram.ui.components.TweetItem
import com.lahsuak.apps.instagram.ui.navigation.NavigationItem
import com.lahsuak.apps.instagram.ui.screen.viewmodel.HomeViewModel
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme
import com.lahsuak.apps.instagram.ui.theme.LIGHT_BLUE
import com.lahsuak.apps.instagram.util.DateUtil
import com.lahsuak.apps.instagram.util.DemoData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TweetScreen(tweetId: String, homeViewModel: HomeViewModel, navController: NavController) {
    val tweetsState = homeViewModel.tweets.collectAsState()
    when (val state = tweetsState.value) {
        is BaseState.Failed -> {
            when (state.error) {
                is ApiFailure.Unknown -> {
                    CenterErrorText(msg = state.error.error)
                }
            }
        }

        BaseState.Loading -> {
            CenterCircularProgressBar()
        }

        is BaseState.Success -> {
            val tweets = state.data
            val tweet = tweets.find { tweet ->
                tweet.id == tweetId
            }
            if (tweet != null) {
                var likes by rememberSaveable {
                    mutableIntStateOf(tweet.likeCount)
                }
                var bookmarks by rememberSaveable {
                    mutableIntStateOf(tweet.bookmarkCount)
                }
                var retweets by rememberSaveable {
                    mutableIntStateOf(tweet.retweetCount)
                }
                var isRetweeted by rememberSaveable {
                    mutableStateOf(false)
                }
                var isLiked by rememberSaveable {
                    mutableStateOf(false)
                }
                var isBookmarked by rememberSaveable {
                    mutableStateOf(false)
                }
                var isFollowed by rememberSaveable {
                    mutableStateOf(false)
                }
                val user = DemoData.demoUser
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {
                    TopAppBar(
                        title = { Text("Tweet", fontSize = 18.sp) },
                        navigationIcon = {
                            Icon(imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier
                                    .padding(
                                        start = 8.dp,
                                        top = 16.dp,
                                        bottom = 16.dp,
                                        end = 16.dp
                                    )
                                    .clickable {
                                        navController.popBackStack()
                                    }
                            )
                        }
                    )
                    Row(
                        Modifier.fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularImage(imageUrl = user.profileImage, imageSize = 45.dp)
                        Spacer(Modifier.width(8.dp))
                        Column(Modifier.weight(1f)) {
                            Text(user.name, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(user.id, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                isFollowed = !isFollowed
                            },
                            colors = ButtonColors(
                                containerColor =
                                if (isFollowed) {
                                    MaterialTheme.colorScheme.background
                                } else {
                                    MaterialTheme.colorScheme.onBackground
                                },
                                contentColor = if (isFollowed) {
                                    LIGHT_BLUE
                                } else {
                                    MaterialTheme.colorScheme.background
                                },
                                disabledContentColor = Color.Gray,
                                disabledContainerColor = Color.LightGray
                            )
                        ) {
                            Text(if (isFollowed) "Following" else "Follow")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(Modifier.fillMaxWidth()) {
                        Text(tweet.description, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            DateUtil.getDateTime(tweet.timeStamp),
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            Modifier.fillMaxWidth(1f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            NumberText(retweets, "Retweets")
                            Spacer(modifier = Modifier.width(8.dp))
                            NumberText(likes, "Likes")
                            Spacer(modifier = Modifier.width(8.dp))
                            NumberText(bookmarks, "Bookmarks")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(thickness = 1.dp, color = Color.Gray)
                        TweetActionRow(
                            isLiked, isRetweeted, isBookmarked,
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
                            navController.navigate(
                                NavigationItem.CreateTweet.route
                            )
                        }
                        Divider(thickness = 1.dp, color = Color.Gray)
                    }
                    LazyColumn(modifier = Modifier.drawBehind {
                        drawLine(
                            color = Color.Gray,
                            start = Offset(60f, 0f),
                            end = Offset(this.size.width / 10, this.size.height)
                        )
                    }) {
                        items(tweets) {
                            TweetItem(tweet = it, onCommentClick = { }) {}
                        }
                    }
                }
            } else {
                CenterCircularProgressBar()
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TweetScreenPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
        }
    }
}