package com.lahsuak.apps.instagram.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: Painter,
    val badgeCount: Int = 0,
)

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colorScheme.background,
    ) {
        val inactiveColor = Color.Gray
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                modifier = modifier.animateContentSize(
                   animationSpec =  tween(5000)
                ),
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = MaterialTheme.colorScheme.onBackground,
                unselectedContentColor = inactiveColor,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = {
                                Text(text = item.badgeCount.toString())
                            }) {
                                Icon(
                                    painter = item.icon,
                                    contentDescription = item.name
                                )
                            }
                        } else {
                            Icon(
                                painter = item.icon,
                                contentDescription = item.name
                            )
                        }
                    }
                })
        }
    }
}
