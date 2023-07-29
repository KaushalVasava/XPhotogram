package com.lahsuak.apps.instagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.lahsuak.apps.instagram.ui.components.BottomIcon
import com.lahsuak.apps.instagram.ui.components.BottomNavItem
import com.lahsuak.apps.instagram.ui.components.BottomNavigationBar
import com.lahsuak.apps.instagram.ui.components.CircularImage
import com.lahsuak.apps.instagram.ui.navhost.AppNavHost
import com.lahsuak.apps.instagram.ui.navigation.NavigationItem
import com.lahsuak.apps.instagram.ui.navigation.Screen
import com.lahsuak.apps.instagram.ui.screen.viewmodel.HomeViewModel
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme
import com.lahsuak.apps.instagram.util.AppConstants.MY_USER_ID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars =
            true
        setContent {
            JetPackComposeBasicTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val homeViewModel: HomeViewModel = viewModel()
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
                                    .fillMaxWidth()
                            ) {
                                BottomNavigationBar(
                                    items = listOf(
                                        BottomNavItem(
                                            NavigationItem.Home.route,
                                            Screen.HOME.name,
                                            icon = rememberVectorPainter(image = Icons.Default.Home)
                                        ),
                                        BottomNavItem(
                                            NavigationItem.Search.route,
                                            Screen.SEARCH.name,
                                            icon = rememberVectorPainter(image = Icons.Default.Search)
                                        ),
                                        BottomNavItem(
                                            NavigationItem.CreatePost.route,
                                            Screen.CREATE_POST.name,
                                            icon = rememberVectorPainter(image = Icons.Default.AddCircle)
                                        ),
                                        BottomNavItem(
                                            NavigationItem.Reels.route,
                                            Screen.REELS.name,
                                            icon = painterResource(id = R.drawable.ic_video)
                                        ),
                                        BottomNavItem(
                                            NavigationItem.Profile.route,
                                            Screen.PROFILE.name,
                                            icon = rememberVectorPainter(image = Icons.Default.Person)
                                        ),
                                    ),
                                    navController = navController,
                                    onItemClick = {
                                        if (it.route == NavigationItem.Profile.route) {
                                            navController.navigate(
                                                "${NavigationItem.Profile.route}/$MY_USER_ID"
                                            )
                                        } else {
                                            navController.navigate(it.route)
                                        }
                                    }
                                )
                            }
                        }
                    ) {
                        AppNavHost(
                            homeViewModel = homeViewModel,
                            navController = navController,
                            modifier = Modifier.padding(it)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        BottomIcon(
                            imageVector = Icons.Default.Home,
                        ) {
                            navController.navigate(Screen.HOME.name)
                        }
                        BottomIcon(
                            imageVector = Icons.Default.Search
                        ) {
                            navController.navigate(NavigationItem.Search.route)
                        }
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null,

                            )
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                        )
                        CircularImage(
                            imageIcon = painterResource(id = R.drawable.mypic),
                            imageSize = 24.dp,
                            isBorderVisible = false,
                            isNameVisible = false,
                            isAnimated = false
                        )
                    }
                }
            ) {
                AppNavHost(homeViewModel = HomeViewModel(), navController, Modifier.padding(it))
            }
        }
    }
}