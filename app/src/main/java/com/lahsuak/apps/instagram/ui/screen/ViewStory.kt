package com.lahsuak.apps.instagram.ui.screen

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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.lahsuak.apps.instagram.models.ApiFailure
import com.lahsuak.apps.instagram.models.BaseState
import com.lahsuak.apps.instagram.models.Story
import com.lahsuak.apps.instagram.models.User
import com.lahsuak.apps.instagram.ui.components.CenterCircularProgressBar
import com.lahsuak.apps.instagram.ui.components.CenterErrorText
import com.lahsuak.apps.instagram.ui.components.CircularImage
import com.lahsuak.apps.instagram.ui.navigation.NavigationItem
import com.lahsuak.apps.instagram.ui.screen.viewmodel.HomeViewModel
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme
import com.lahsuak.apps.instagram.util.DemoData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewStory(
    storyId: String,
    userId: String,
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    val storyState by homeViewModel.stories.collectAsState()
    var pageCount by remember {
        mutableIntStateOf(1)
    }
    val pagerState = rememberPagerState(pageCount = {
        pageCount
    })
    HorizontalPager(state = pagerState) { page ->
        when (val state = storyState) {
            is BaseState.Failed -> {
                when (state.error) {
                    is ApiFailure.Unknown -> CenterErrorText(msg = state.error.error)
                }
            }

            BaseState.Loading -> CenterCircularProgressBar()
            is BaseState.Success -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    val stories = state.data.filter {
                        it.userId == userId
                    }
                    pageCount = stories.size
                    val user = homeViewModel.getUserById(stories[page].userId)
                    if (user != null) {
                        StoryItem(stories[page], user, {
                             navController.navigate(
                                 "${NavigationItem.Profile.route}/${user.id}"
                             )
                        }) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
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
        AsyncImage(
            story.image,
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
                    imageUrl = user.profileImage,
                    imageSize = 40.dp,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .clickable {
                            onImageClick()
                        }
                )
                Text(
                    user.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
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
                verticalAlignment = Alignment.CenterVertically,
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
                        .padding(start = 8.dp)
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
                story = DemoData.demoStory,
                user = DemoData.demoUser,
                onImageClick = {}
            ) {}
        }
    }
}
