package com.lahsuak.apps.instagram.ui.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lahsuak.apps.instagram.ui.navigation.NavigationItem
import com.lahsuak.apps.instagram.ui.screen.ChatListScreen
import com.lahsuak.apps.instagram.ui.screen.ChatScreen
import com.lahsuak.apps.instagram.ui.screen.CreatePostScreen
import com.lahsuak.apps.instagram.ui.screen.CreateTweetScreen
import com.lahsuak.apps.instagram.ui.screen.HomeScreen
import com.lahsuak.apps.instagram.ui.screen.NotificationScreen
import com.lahsuak.apps.instagram.ui.screen.ProfileScreen
import com.lahsuak.apps.instagram.ui.screen.ReelsScreen
import com.lahsuak.apps.instagram.ui.screen.SearchScreen
import com.lahsuak.apps.instagram.ui.screen.TweetListScreen
import com.lahsuak.apps.instagram.ui.screen.TweetScreen
import com.lahsuak.apps.instagram.ui.screen.UserFollowListScreen
import com.lahsuak.apps.instagram.ui.screen.ViewPostScreen
import com.lahsuak.apps.instagram.ui.screen.ViewStory
import com.lahsuak.apps.instagram.ui.screen.viewmodel.HomeViewModel
import com.lahsuak.apps.instagram.util.AppConstants.MY_USER_ID

@Composable
fun AppNavHost(
    homeViewModel: HomeViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = NavigationItem.Home.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreen(homeViewModel = homeViewModel, navController = navController)
        }
        composable(NavigationItem.Search.route) {
            SearchScreen(homeViewModel = homeViewModel, navController = navController)
        }
        composable(NavigationItem.CreatePost.route) {
            CreatePostScreen()
        }
        composable(NavigationItem.TweetList.route) {
            TweetListScreen(homeViewModel, navController)
        }
        composable(
            "${NavigationItem.Tweet.route}/{tweetId}",
            arguments = listOf(
                navArgument("tweetId") {
                    type = NavType.StringType
                },
            )
        ) {
            val tweetId = it.arguments?.getString("tweetId")
            tweetId?.let { id ->
                TweetScreen(id, homeViewModel, navController)
            }
        }
        composable(NavigationItem.CreateTweet.route) {
            CreateTweetScreen(homeViewModel = homeViewModel, navController = navController)
        }
        composable(NavigationItem.Reels.route) {
            ReelsScreen(homeViewModel = homeViewModel, navController)
        }
        composable(NavigationItem.ChatList.route) {
            ChatListScreen(homeViewModel, navController = navController)
        }
        composable(
            "${NavigationItem.Followers.route}/{isFollowing}/{userId}",
            arguments = listOf(
                navArgument("isFollowing") {
                    type = NavType.BoolType
                },
                navArgument("userId") {
                    type = NavType.StringType
                },
            )
        ) {
            val isFollower = it.arguments?.getBoolean("isFollowing") ?: false
            val userId = it.arguments?.getString("userId") ?: MY_USER_ID
            UserFollowListScreen(
                homeViewModel = homeViewModel,
                isFollowing = isFollower,
                userId = userId,
                navController = navController
            )
        }
        composable("${NavigationItem.Chat.route}/{userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.StringType
                }
            )) {
            val userId = it.arguments?.getString("userId") ?: "userid"
            ChatScreen(userId, homeViewModel = homeViewModel, navController = navController)
        }
        composable(
            "${NavigationItem.Profile.route}/{userid}",
            arguments = listOf(
                navArgument("userid") {
                    type = NavType.StringType
                }
            )) {
            val userId = it.arguments?.getString("userid")
            ProfileScreen(
                userId = userId ?: MY_USER_ID,
                homeViewModel = homeViewModel,
                navController = navController
            )
        }
        composable("${NavigationItem.ViewPost.route}/{postId}",
            arguments = listOf(
                navArgument("postId") {
                    type = NavType.StringType
                }
            )) {
            val postId = it.arguments?.getString("postId")
            postId?.let { id ->
                ViewPostScreen(id, homeViewModel = homeViewModel, navController = navController)
            }
        }
        composable("${NavigationItem.ViewStory.route}/{storyId}/{userId}",
            arguments = listOf(
                navArgument("storyId") {
                    type = NavType.StringType
                }, navArgument("userId") {
                    type = NavType.StringType
                }
            )) {
            val storyId = it.arguments?.getString("storyId")
            val userId = it.arguments?.getString("userId")
            if (storyId != null && userId != null) {
                ViewStory(
                    storyId,
                    userId,
                    homeViewModel = homeViewModel,
                    navController = navController
                )
            }
        }
        composable(NavigationItem.Notification.route) {
            NotificationScreen(homeViewModel = homeViewModel, navController = navController)
        }
    }
}