package com.lahsuak.apps.instagram.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lahsuak.apps.instagram.models.User
import com.lahsuak.apps.instagram.ui.components.ActionButton
import com.lahsuak.apps.instagram.ui.components.CircularImage
import com.lahsuak.apps.instagram.ui.navigation.NavigationItem
import com.lahsuak.apps.instagram.ui.screen.viewmodel.HomeViewModel
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme
import com.lahsuak.apps.instagram.ui.theme.LIGHT_BLUE
import com.lahsuak.apps.instagram.util.AppConstants.MY_USER_ID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFollowListScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
    isFollowing: Boolean,
    userId: String,
) {
    val user = homeViewModel.getUserById(userId)
    if (user != null) {
        val users = if (isFollowing) {
            homeViewModel.getFollowings(user)
        } else {
            homeViewModel.getFollowers(user)
        }
        Column {
            TopAppBar(
                title = {
                    Text(if (isFollowing) "Following" else "Followers", fontSize = 16.sp)
                },
                navigationIcon = {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }
            )
            LazyColumn(Modifier.padding(horizontal = 16.dp)) {
                items(users.filterNot {
                    it.id == userId
                }) {
                    FollowItem(user = it,
                        isFollowing = isFollowing || (it.followingIds.any { id ->
                            id == userId
                        } && user.followingIds.any { id ->
                            id == it.id
                        }),
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .clickable {
                                navController.navigate(
                                    "${NavigationItem.Profile.route}/${it.id}"
                                )
                            }
                    )
                }
            }
        }
    } else {
        Text("No Data")
    }
}

@Composable
fun FollowItem(user: User, modifier: Modifier, isFollowing: Boolean) {
    var isFollowed by remember {
        mutableStateOf(isFollowing)
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularImage(imageUrl = user.profileImage, imageSize = 50.dp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = user.name,
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
        ActionButton(
            text = if (isFollowed) "Following" else "Follow",
            textColor = if (isFollowed) MaterialTheme.colorScheme.onBackground else Color.White,
            backgroundColor =
            if (isFollowed)
                MaterialTheme.colorScheme.inverseOnSurface
            else
                LIGHT_BLUE,
            modifier = Modifier.clickable {
                isFollowed = !isFollowed
            }
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FollowListPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val homeViewModel: HomeViewModel = viewModel()

            UserFollowListScreen(
                isFollowing = false,
                userId = MY_USER_ID,
                navController = rememberNavController(),
                homeViewModel = homeViewModel
            )
        }
    }
}