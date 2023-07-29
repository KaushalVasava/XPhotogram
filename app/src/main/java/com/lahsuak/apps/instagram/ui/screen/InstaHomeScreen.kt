package com.lahsuak.apps.instagram.ui.screen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lahsuak.apps.instagram.R
import com.lahsuak.apps.instagram.ui.components.CircularImage
import com.lahsuak.apps.instagram.ui.components.PostItem
import com.lahsuak.apps.instagram.ui.navigation.NavigationItem
import com.lahsuak.apps.instagram.ui.screen.viewmodel.HomeViewModel
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme
import com.lahsuak.apps.instagram.util.AppConstants.MY_USER_ID

private const val TAG = "TAG"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstaHomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    val user = homeViewModel.getUserById(MY_USER_ID)

    if (user != null) {
        val followings = homeViewModel.getUsersByIds(user.followingIds + user.id)
        val stories =
            homeViewModel.getStoriesByUserIds(user.followingIds).distinctBy { it.userId }
        val posts = homeViewModel.getPostsByUserIds(user.followingIds + user.id)

        Column(modifier = modifier) {
            TopAppBar(title = {
                Text(
                    "Instagram Compose",
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.ExtraBold
                )
            }, actions = {
                Icon(
                    painterResource(id = R.drawable.ic_chat),
                    contentDescription = null,
                    Modifier
                        .padding(8.dp)
                        .clickable {
                            navController.navigate(NavigationItem.ChatList.route)
                        }
                )
            })
            LazyColumn {
                item {
                    LazyRow(Modifier.padding(start = 8.dp)) {
                        item {
                            Box(
                                contentAlignment = Alignment.BottomEnd,
                                modifier = Modifier.clickable {
                                    Log.d(TAG, "InstaHomeScreen: new story")
                                }) {
                                CircularImage(
                                    imageIcon = painterResource(id = R.drawable.mypic),
                                    isBorderVisible = false,
                                    isNameVisible = true,
                                    name = "Your story",
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .clickable {
                                            navController.navigate(NavigationItem.CreatePost.route)
                                        },
                                )
                                Icon(
                                    tint = Color.Red,
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = null,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        items(stories) { story ->
                            val usr = followings.find {
                                story.userId == it.id
                            }
                            if (usr != null) {
                                CircularImage(
                                    imageIcon = painterResource(id = usr.profileImage),
                                    isBorderVisible = true,
                                    isNameVisible = true,
                                    name = usr.name,
                                    isAnimated = true,
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .clickable {
                                            navController.navigate("${NavigationItem.ViewStory.route}/${story.id}")
                                        },
                                )
                            }
                        }
                    }
                }
                items(posts) { post ->
                    val usr = followings.find {
                        post.userId == it.id
                    }
                    if (usr != null) {
                        PostItem(post, usr, onImageClick = {
                            navController.navigate(
                                "${NavigationItem.Profile.route}/${usr.id}"
                            )
                        }) {
                            // open bottom sheet with more options
                        }
                    }
                }
            }
        }
    } else {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            InstaHomeScreen(
                homeViewModel = HomeViewModel(),
                navController = rememberNavController()
            )
        }
    }
}