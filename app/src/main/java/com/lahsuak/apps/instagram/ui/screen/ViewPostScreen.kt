package com.lahsuak.apps.instagram.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lahsuak.apps.instagram.ui.components.PostItem
import com.lahsuak.apps.instagram.ui.navigation.NavigationItem
import com.lahsuak.apps.instagram.ui.screen.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPostScreen(
    postId: String,
    homeViewModel: HomeViewModel,
    navController: NavController,
) {

    val postState by remember {
        mutableStateOf(homeViewModel.getPostById(postId))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text("Explore", fontSize = 16.sp)
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "back",
                    )
                }
            }
        )
        val post = postState
        if (post != null) {
            val user = homeViewModel.getUserById(post.userId)
            if (user != null) {
                PostItem(post, user, onImageClick = {
                    navController.navigate(
                        "${NavigationItem.Profile.route}/${user.id}"
                    )
                }) {
                    //do something on more click
                }
            }
        } else {
            Text("No Post")
        }
    }
}