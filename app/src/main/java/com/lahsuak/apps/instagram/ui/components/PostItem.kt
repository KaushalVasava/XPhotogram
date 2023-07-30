package com.lahsuak.apps.instagram.ui.components

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lahsuak.apps.instagram.R
import com.lahsuak.apps.instagram.models.Post
import com.lahsuak.apps.instagram.models.User
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme
import com.lahsuak.apps.instagram.util.AppConstants

@Composable
fun PostItem(
    post: Post,
    user: User,
    onImageClick: () -> Unit,
    onMoreClick: () -> Unit,
) {
    var likes by remember {
        mutableIntStateOf(post.likeCount)
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Column(Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularImage(
                imageUrl = user.profileImage,
                imageSize = 40.dp,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clickable {
                        onImageClick()
                    }
            )
            Column(modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp)) {
                Text(user.name)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "location",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                Modifier
                    .padding(8.dp)
                    .clickable {
                        onMoreClick()
                    }
            )
        }
        PostImage(imageUrl = post.postImage)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            var isFavorite by remember { mutableStateOf(false) }

            IconToggleButton(
                checked = isFavorite,
                onCheckedChange = {
                    if (isFavorite)
                        likes--
                    else
                        likes++
                    isFavorite = !isFavorite
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                val transition = updateTransition(isFavorite, label = "favorite")
                val tint by animateColorAsState(
                    targetValue = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onBackground,
                    label = "tint",
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
            Icon(
                painterResource(id = R.drawable.ic_comment),
                contentDescription = null,
                Modifier.padding(vertical = 8.dp)
            )
            Icon(
                Icons.Default.Send,
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        start = 12.dp, top = 8.dp, bottom = 8.dp
                    )
            )
            Spacer(modifier = Modifier.weight(1f))
            ToggleIconButton(
                enableTint = Color.Black,
                enableIcon = painterResource(id = R.drawable.ic_bookmark),
                disableIcon = painterResource(id = R.drawable.ic_bookmark_border)
            )
        }
        Text("$likes likes", Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = post.caption,
            modifier = Modifier
                .animateContentSize()
                .padding(horizontal = 16.dp)
                .clickable {
                    isExpanded = !isExpanded
                },
            maxLines = if (isExpanded) 5 else 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PostItemPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            PostItem(
                Post(
                    "123451",
                    AppConstants.MY_USER_ID,
                    postImage = "https://cdn.pixabay.com/photo/2023/05/10/18/20/plant-7984681_1280.jpg",
                    caption = "I'm so happy bcz I create multiple apps and published in the play store",
                    likeCount = 8
                ),
                User(
                    id = AppConstants.MY_USER_ID,
                    name = "Kaushal",
                    profileImage = "https://cdn.pixabay.com/photo/2023/05/23/15/26/bengal-cat-8012976_1280.jpg",
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
