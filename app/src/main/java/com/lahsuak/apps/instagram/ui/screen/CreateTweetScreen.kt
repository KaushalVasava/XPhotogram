package com.lahsuak.apps.instagram.ui.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lahsuak.apps.instagram.R
import com.lahsuak.apps.instagram.models.Tweet
import com.lahsuak.apps.instagram.ui.components.CircularImage
import com.lahsuak.apps.instagram.ui.screen.viewmodel.HomeViewModel
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme
import com.lahsuak.apps.instagram.util.DateUtil
import com.lahsuak.apps.instagram.util.DemoData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTweetScreen(homeViewModel: HomeViewModel, navController: NavController) {
    val user = DemoData.demoUser
    var text by rememberSaveable {
        mutableStateOf("")
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                homeViewModel.updateTweet(tweet = Tweet(description = text, userId = user.id))
                navController.popBackStack()
            }) {
                Icon(Icons.Default.Done, contentDescription = "Add")
            }
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            TopAppBar(
                title = { Text("Create Tweet", fontSize = 18.sp) },
                navigationIcon = {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription =
                        stringResource(id = R.string.back),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }
            )
            Row(
                Modifier
                    .fillMaxWidth(1f)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularImage(imageUrl = user.profileImage, imageSize = 45.dp)
                Spacer(Modifier.width(8.dp))
                Text(
                    user.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    DateUtil.getDateTime(System.currentTimeMillis()),
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            TextField(
                value = text,
                onValueChange = { q ->
                    text = q
                },
                placeholder = {
                    Text("Write your thoughts", color = Color.Gray)
                },
                modifier = Modifier.fillMaxSize(1f)

            )
        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TweetItemPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CreateTweetScreen(homeViewModel = viewModel(), rememberNavController())
        }
    }
}