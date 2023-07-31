package com.lahsuak.apps.instagram.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lahsuak.apps.instagram.models.ApiFailure
import com.lahsuak.apps.instagram.models.BaseState
import com.lahsuak.apps.instagram.models.Notification
import com.lahsuak.apps.instagram.ui.components.CenterCircularProgressBar
import com.lahsuak.apps.instagram.ui.components.CenterErrorText
import com.lahsuak.apps.instagram.ui.components.CircularImage
import com.lahsuak.apps.instagram.ui.screen.viewmodel.HomeViewModel
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme
import com.lahsuak.apps.instagram.util.DateUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    val notificationsState by homeViewModel.notifications.collectAsState()
    Column {
        TopAppBar(title = {
            Text(
                "Notifications",
            )
        }, navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navController.popBackStack() }
            )
        }
        )
        when (val state = notificationsState) {
            is BaseState.Failed -> {
                when (state.error) {
                    is ApiFailure.Unknown -> CenterErrorText(msg = state.error.error)
                }
            }

            BaseState.Loading -> {
                CenterCircularProgressBar()
            }

            is BaseState.Success -> {
                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                    items(state.data) {
                        NotificationItem(notification = it)
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularImage(imageUrl = notification.image, imageSize = 45.dp)
        Spacer(modifier = Modifier.width(8.dp))
        Column(Modifier.fillMaxWidth()) {
            Text(notification.title, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                DateUtil.getDateTime(notification.timeDate),
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NotificationPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            val homeViewModel: HomeViewModel = viewModel()

            NotificationScreen(
                homeViewModel = homeViewModel,
                navController = rememberNavController()
            )
        }
    }
}