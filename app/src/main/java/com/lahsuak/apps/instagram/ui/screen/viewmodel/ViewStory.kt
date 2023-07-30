package com.lahsuak.apps.instagram.ui.screen.viewmodel

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lahsuak.apps.instagram.R
import com.lahsuak.apps.instagram.models.Story
import com.lahsuak.apps.instagram.models.User
import com.lahsuak.apps.instagram.ui.components.CircularImage
import com.lahsuak.apps.instagram.ui.navigation.NavigationItem
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme
import com.lahsuak.apps.instagram.util.AppConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewStory(
    storyId: String,
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    val story = homeViewModel.stories.find {
        it.id == storyId
    }
    if (story != null) {
        val user = homeViewModel.getUserById(story.userId)
        if (user != null) {
            StoryItem(story, user, onImageClick = {
                navController.navigate(
                    "${NavigationItem.Profile.route}/${user.id}"
                )
            }) {
                navController.popBackStack()
            }
        }
    } else {
        Text("No Post")
    }
}

@Composable
fun StoryItem(
    story: Story,
    user: User,
    onImageClick: () -> Unit,
    onMoreClick: () -> Unit,
) {
    var likes by remember {
        mutableIntStateOf(story.likeCount)
    }
    var isTouched by remember {
        mutableStateOf(true)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painterResource(id = story.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    isTouched = !isTouched
                },
            contentScale = ContentScale.FillBounds
        )
        AnimatedVisibility(
            visible = isTouched,
            enter = slideInVertically(),
            exit = slideOutVertically(),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularImage(
                    imageIcon = painterResource(id = user.profileImage),
                    imageSize = 40.dp,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .clickable {
                            onImageClick()
                        }
                )
                Text(user.name, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    Modifier
                        .padding(8.dp)
                        .clickable {
                            onMoreClick()
                        }
                )
            }
        }
        AnimatedVisibility(
            visible = isTouched,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                var isFavorite by remember { mutableStateOf(false) }
                var text by remember {
                    mutableStateOf("")
                }
                TextField(
                    value = text,
                    placeholder = {
                        Text("Comment")
                    },
                    onValueChange = {
                        text = it
                    },
                    modifier = Modifier.clip(RoundedCornerShape(16.dp))
                )
                IconToggleButton(
                    checked = isFavorite,
                    onCheckedChange = {
                        if (isFavorite)
                            likes--
                        else
                            likes++
                        isFavorite = !isFavorite
                    },
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 2.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                ) {
                    val transition = updateTransition(isFavorite, label = "favorite")
                    val tint by animateColorAsState(
                        targetValue = if (isFavorite) Color.Red else Color.Black,
                        label = "tint"
                    )
                    // om below line we are specifying transition
                    val size by transition.animateDp(
                        transitionSpec = {
                            // on below line we are specifying transition
                            if (false isTransitioningTo true) {
                                // on below line we are specifying key frames
                                keyframes {
                                    // on below line we are specifying animation duration
                                    durationMillis = 1000
                                    // on below line we are specifying animations.
                                    30.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                                    35.dp at 15 with FastOutLinearInEasing // for 15-75 ms
                                    40.dp at 75 // ms
                                    35.dp at 150 // ms
                                }
                            } else {
                                spring(stiffness = Spring.StiffnessVeryLow)
                            }
                        },
                        label = "Size"
                    ) {
                        if (it)
                            30.dp
                        else
                            30.dp
                    }
                    Icon(
                        tint = tint,
                        painter = if (isFavorite) {
                            rememberVectorPainter(image = Icons.Filled.Favorite)
                        } else {
                            rememberVectorPainter(image = Icons.Filled.FavoriteBorder)
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .size(size)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StoryItemPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            StoryItem(
                Story("12429", AppConstants.MY_USER_ID, image = R.drawable.mypic),
                User(
                    id = AppConstants.MY_USER_ID,
                    name = "Kaushal",
                    profileImage = R.drawable.mypic,
                    bio = "Android developer | Nature Lover",
                    links = listOf("https://github.com//KaushalVasava"),
                    followerIds = listOf("12346", "12347", "12348", "12349"),
                    followingIds = listOf("12346", "12347", "12348", "12349", "12345"),
                    postIds = listOf(
                        "123464",
                        "123465",
                        "123466",
                        "123467",
                        "123468",
                        "123469"
                    ),
                    storyIds = listOf("12411", "12412", "12413", "12429", "12428"),
                ), {}, {}
            )
        }
    }
}
