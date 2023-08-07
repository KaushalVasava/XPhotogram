package com.lahsuak.apps.instagram.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lahsuak.apps.instagram.R
import com.lahsuak.apps.instagram.models.Chat
import com.lahsuak.apps.instagram.models.Message
import com.lahsuak.apps.instagram.ui.components.CircularImage
import com.lahsuak.apps.instagram.ui.navigation.NavigationItem
import com.lahsuak.apps.instagram.ui.screen.viewmodel.HomeViewModel
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme
import com.lahsuak.apps.instagram.ui.theme.LIGHT_BLUE
import com.lahsuak.apps.instagram.util.AppConstants.MY_USER_ID
import com.lahsuak.apps.instagram.util.DemoData
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    userId: String,
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    var isProgress by remember {
        mutableStateOf(false)
    }
    if (isProgress) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    val user = homeViewModel.getUserById(userId)
    val myUserId = MY_USER_ID
    isProgress = false
    if (user != null) {
        val chat = Chat(
            senderImage = user.profileImage,
            receiverImage = DemoData.urls.first(),
            msgs = listOf(
                Message("Hello", "12:45pm", userId = myUserId, isSeen = true),
                Message("Hey", "12:46pm", userId = userId, isSeen = true),
                Message("How are you?", "12:47pm", userId = myUserId, isSeen = true),
                Message("I'm good, hbu?", "12:49pm", userId = userId, isSeen = true),
                Message("I'm good too, thanks", "12:53pm", userId = myUserId, isSeen = true),
                Message(
                    "What are you doing",
                    "12:56pm",
                    userId = userId,
                    isSeen = true
                ),
                Message(
                    "?",
                    "12:56pm",
                    userId = userId,
                    isSeen = true
                ),
                Message(
                    "Nothing much, just code refactoring, wbu?",
                    "1:00pm",
                    userId = myUserId,
                    isSeen = false
                ),
                Message(
                    "Just resting", "1:00pm", userId = userId,
                ),
            )
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularImage(
                                imageUrl = user.profileImage,
                                imageSize = 50.dp,
                                modifier = Modifier.clickable {
                                    navController.navigate(
                                        "${NavigationItem.Profile.route}/${user.id}"
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(user.name, fontSize = 18.sp)
                                Box(
                                    Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color.Green)
                                )
                            }
                        }
                    },
                    actions = {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "menu",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }, navigationIcon = {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back",
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    navController.popBackStack()
                                })
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(chat.msgs) {
                        if (it.userId == myUserId) {
                            ChatItem(chat.senderImage, it, true)
                        } else {
                            ChatItem(chat.receiverImage, it, false)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(56.dp))
                    }
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var text by rememberSaveable {
                    mutableStateOf("")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_camera),
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(LIGHT_BLUE)
                        .padding(4.dp),
                    tint = Color.White,
                    contentDescription = "Camera",
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextField(
                    value = text,
                    placeholder = {
                        Text(
                            stringResource(R.string.message_hint),
                            color = Color.Gray,
                        )
                    },
                    onValueChange = {
                        text = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_mic), "Mic",
                    modifier = Modifier.padding(4.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_image), "Gallery",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable {
                            navController.navigate(NavigationItem.CreatePost.route)
                        }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.Outlined.AddCircle, "Add",
                    modifier = Modifier.padding(4.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun ChatItem(
    userImageUrl: String,
    message: Message,
    isLeft: Boolean,
) {
    val horizontalArrangement by remember {
        mutableStateOf(
            if (isLeft)
                Arrangement.Start
            else
                Arrangement.End
        )
    }
    Row(
        Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxSize(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        if (isLeft) {
            CircularImage(imageUrl = userImageUrl, imageSize = 50.dp)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column {
            val status by remember {
                mutableStateOf(
                    if (message.isSeen)
                        "Seen"
                    else
                        "Sent"
                )
            }
            val align by remember {
                mutableStateOf(
                    if (isLeft)
                        Alignment.Start
                    else
                        Alignment.End
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.horizontalGradient(listOf(Color.Green, Color.Cyan))
                    )
                    .drawBehind {
                        repeat(Random.nextInt(2,4)) {
                            drawCircle(
                                Brush.radialGradient(colors = listOf(Color.Red, Color.Blue)),
                                alpha = 0.06f,
                                radius = Random
                                    .nextInt(5, 100)
                                    .toFloat(),
                                center = Offset(
                                    Random
                                        .nextInt(0, size.minDimension.toInt())
                                        .toFloat(),
                                    Random
                                        .nextInt(0, size.maxDimension.toInt())
                                        .toFloat()
                                )
                            )
                        }
                    }
                    .padding(4.dp)
                    .alpha(0.7f)
            ) {
                Text(
                    message.msg,
                    modifier = Modifier.widthIn(20.dp, 240.dp),
                    fontSize = 16.sp, color = Color.Black,
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    message.timeStamp,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                status,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.align(align)
            )
        }
        if (!isLeft) {
            Spacer(modifier = Modifier.width(8.dp))
            CircularImage(imageUrl = userImageUrl, imageSize = 50.dp)
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ChatScreenPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            ChatItem(
                DemoData.urls.first(), Message(
                    msg = "Hello how are you?, What are you doing",
                    "5:10pm",
                    userId = MY_USER_ID
                ), isLeft = false
            )
        }
    }
}