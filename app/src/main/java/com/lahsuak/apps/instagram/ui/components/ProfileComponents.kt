package com.lahsuak.apps.instagram.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.lahsuak.apps.instagram.R
import com.lahsuak.apps.instagram.models.Post
import com.lahsuak.apps.instagram.models.Story
import com.lahsuak.apps.instagram.models.User
import com.lahsuak.apps.instagram.models.UserType
import com.lahsuak.apps.instagram.ui.navigation.NavigationItem
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme
import com.lahsuak.apps.instagram.ui.theme.LIGHT_BLUE
import com.lahsuak.apps.instagram.util.openTab

@Composable
fun MiddlePart(
    user: User,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CircularImage(
                imageUrl = user.profileImage,
                imageSize = 80.dp
            )
            StateInfo(modifier = Modifier.weight(7f), user, navController)
        }
    }
}

@Composable
fun StateInfo(modifier: Modifier, user: User, navController: NavController) {
    StateItem("Posts", user.postIds.size.toString(), modifier)
    StateItem("Followers", user.followerIds.size.toString(), modifier.clickable {
        navController.navigate(
            "${NavigationItem.Followers.route}/false/${user.id}"
        )
    })
    StateItem("Following", user.followingIds.size.toString(), modifier.clickable {
        navController.navigate(
            "${NavigationItem.Followers.route}/true/${user.id}"
        )
    })
}

@Composable
fun StateItem(
    desc: String,
    count: String,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        Text(
            text = count,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = desc,
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif
        )
    }
}

@Composable
fun ProfileDescription(
    displayName: String,
    description: String,
    url: String?,
    followedBy: List<String>,
    otherCount: Int,
    modifier: Modifier = Modifier,
) {
    val letterSpacing = 0.5.sp
    val lineHeight = 16.sp
    val context = LocalContext.current
    Column(
        modifier = modifier
    ) {
        Text(
            text = displayName,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        Text(
            text = description,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        if (url != null) {
            Text(
                text = url,
                fontWeight = FontWeight.Normal,
                color = LIGHT_BLUE,
                letterSpacing = letterSpacing,
                lineHeight = lineHeight,
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    openTab(context, url)
                }
            )
        }
        if (followedBy.isNotEmpty()) {
            Text(text = buildAnnotatedString {
                val boldStyle = SpanStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                append("Followed by ")
                followedBy.forEachIndexed { index, name ->
                    pushStyle(boldStyle)
                    append(name)
                    pop()
                    if (index < followedBy.size - 1) {
                        append(", ")
                    }
                    if (otherCount > 2) {
                        append(" and ")
                        pushStyle(boldStyle)
                        append("$otherCount others")
                    }
                }
            })
        }
    }
}

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier,
    userType: UserType,
) {
    val minWidth = 130.dp
    val firstText = when (userType) {
        UserType.FOLLOWING -> {
            "Following"
        }

        UserType.FOLLOWER -> {
            "Follow"
        }

        else -> {
            "Edit profile"
        }
    }
    val middleText = if (userType == UserType.ADMIN) {
        "Share profile"
    } else {
        "Message"
    }

    val firstBtnColor = if (userType == UserType.FOLLOWER) {
        LIGHT_BLUE
    } else {
        MaterialTheme.colorScheme.inverseOnSurface
    }
    val followTextColor = if (userType == UserType.FOLLOWER) {
        Color.White
    } else {
        MaterialTheme.colorScheme.onBackground
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        ActionButton(
            text = firstText,
            backgroundColor = firstBtnColor,
            textColor = followTextColor,
            modifier = Modifier
                .defaultMinSize(
                    minWidth = minWidth
                )
        )
        ActionButton(
            text = middleText,
            modifier = Modifier
                .defaultMinSize(
                    minWidth = minWidth
                )
        )
        ActionButton(
            icon = Icons.Default.KeyboardArrowDown,
        )
    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.inverseOnSurface,
    icon: ImageVector? = null,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .padding(4.dp)
    ) {
        if (text != null) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = textColor
            )
        }
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun HighlightSection(
    modifier: Modifier = Modifier,
    highlights: List<Story>,
    navController: NavController,
) {
    LazyRow(modifier = modifier) {
        items(highlights) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        navController.navigate("${NavigationItem.ViewStory.route}/${it.id}/${it.userId}")
                    }
            ) {
                CircularImage(
                    imageUrl = it.image,
                    imageSize = 65.dp,
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = it.name ?: "",
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun PostTabView(
    modifier: Modifier = Modifier,
    imageWithText: List<Pair<String, Painter>>,
    onTabSelected: (selectedIndex: Int) -> Unit,
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val inactiveColor = Color.Gray
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = Color.Black,
        modifier = modifier.padding(vertical = 1.dp)
    ) {
        imageWithText.forEachIndexed { index, item ->
            Tab(selected = selectedTabIndex == index,
                selectedContentColor = MaterialTheme.colorScheme.onBackground,
                unselectedContentColor = inactiveColor,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                }
            ) {
                Icon(
                    painter = item.second,
                    contentDescription = item.first,
                    tint = if (selectedTabIndex == index)
                        MaterialTheme.colorScheme.onBackground
                    else
                        inactiveColor,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(20.dp)
                )
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun PostSection(
    posts: List<Post>,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .scale(1.01f)
    ) {
        items(posts) {
            AsyncImage(
                it.postImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.background
                    )
                    .clickable {
                        navController.navigate(
                            "${NavigationItem.ViewPost.route}/${it.id}"
                        )
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ActionButtonPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            PostTabView(
                imageWithText = listOf(
                    "Posts" to
                            painterResource(id = R.drawable.ic_grid),
                    "Reels" to painterResource(id = R.drawable.ic_video),
                    "Profile" to
                            painterResource(id = R.drawable.ic_profile),
                )
            ) {}
        }
    }
}
