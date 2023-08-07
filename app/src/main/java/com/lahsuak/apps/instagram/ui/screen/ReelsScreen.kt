package com.lahsuak.apps.instagram.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.lahsuak.apps.instagram.R
import com.lahsuak.apps.instagram.models.ApiFailure
import com.lahsuak.apps.instagram.models.BaseState
import com.lahsuak.apps.instagram.ui.components.CenterCircularProgressBar
import com.lahsuak.apps.instagram.ui.components.CenterErrorText
import com.lahsuak.apps.instagram.ui.components.CircularImage
import com.lahsuak.apps.instagram.ui.components.ToggleIconButton
import com.lahsuak.apps.instagram.ui.navigation.NavigationItem
import com.lahsuak.apps.instagram.ui.screen.viewmodel.HomeViewModel
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme
import com.lahsuak.apps.instagram.util.AppConstants.MY_USER_ID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReelsScreen(homeViewModel: HomeViewModel, navController: NavController) {
    val storyState by homeViewModel.stories.collectAsState()
    var pageCount by remember{
        mutableIntStateOf(1)
    }
    val pagerState = rememberPagerState(pageCount = {
        pageCount
    })
    VerticalPager(state = pagerState) { page ->
        when (val state = storyState) {
            is BaseState.Failed -> {
                when (state.error) {
                    is ApiFailure.Unknown -> CenterErrorText(msg = state.error.error)
                }
            }

            BaseState.Loading -> CenterCircularProgressBar()
            is BaseState.Success -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    val stories = state.data
                    pageCount = stories.size
                    AsyncImage(
                        stories[page].image,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                    Column(Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                stringResource(R.string.reels),
                                fontSize = 18.sp,
                                color = Color.White,
                                modifier = Modifier.padding(16.dp)
                            )
                            Icon(
                                painterResource(id = R.drawable.ic_camera),
                                contentDescription = "camera",
                                tint = Color.White,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            CircularImage(
                                imageUrl = stories[page].image,
                                imageSize = 50.dp,
                                modifier = Modifier.clickable {
                                    navController.navigate(
                                        "${NavigationItem.Profile.route}/${MY_USER_ID}"
                                    )
                                }
                            )
                            Spacer(Modifier.width(8.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.height(50.dp)
                                ) {
                                    Text(
                                        text = stories[page].name ?: "Test",
                                        color = Color.White,
                                        maxLines = 1,
                                        modifier = Modifier.weight(1f),
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        stringResource(R.string.follow),
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        modifier = Modifier
                                            .border(
                                                width = 1.dp,
                                                color = Color.White,
                                                shape = RoundedCornerShape(6.dp)
                                            )
                                            .padding(vertical = 4.dp, horizontal = 8.dp)
                                    )
                                }
                            }
                            Spacer(Modifier.width(8.dp))
                            Column(
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    ToggleIconButton(
                                        enableIcon =
                                        rememberVectorPainter(image = Icons.Default.Favorite),
                                        disableIcon =
                                        rememberVectorPainter(image = Icons.Default.FavoriteBorder),
                                        disableTint = Color.White
                                    )
                                    Text(
                                        stories[page].likeCount.toString(),
                                        maxLines = 1,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                IconText(
                                    count = 1889,
                                    tint = Color.White,
                                    icon = rememberVectorPainter(image = Icons.Outlined.Email)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                IconText(
                                    count = 405,
                                    tint = Color.White,
                                    icon = rememberVectorPainter(image = Icons.Outlined.Send)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Icon(
                                    Icons.Default.MoreVert,
                                    tint = Color.White,
                                    contentDescription = null
                                )
                            }
                        }

                        Text(
                            text = stories[page].name?:"Something new",
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            fontSize = 12.sp,
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    count: Int,
    icon: Painter,
    tint: Color = MaterialTheme.colorScheme.onBackground,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(40.dp)
    ) {
        Icon(icon, tint = tint, contentDescription = null)
        Spacer(modifier = Modifier.height(8.dp))
        Text(count.toString(), color = tint, textAlign = TextAlign.Center, maxLines = 1)
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReelsPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val homeViewModel: HomeViewModel = viewModel()
            ReelsScreen(homeViewModel = homeViewModel, rememberNavController())
        }
    }
}
