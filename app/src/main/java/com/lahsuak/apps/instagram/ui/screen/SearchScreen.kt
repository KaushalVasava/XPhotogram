package com.lahsuak.apps.instagram.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
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
import com.lahsuak.apps.instagram.models.Post
import com.lahsuak.apps.instagram.models.User
import com.lahsuak.apps.instagram.ui.components.CenterCircularProgressBar
import com.lahsuak.apps.instagram.ui.components.CenterErrorText
import com.lahsuak.apps.instagram.ui.navigation.NavigationItem
import com.lahsuak.apps.instagram.ui.screen.viewmodel.HomeViewModel
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    val postsState by homeViewModel.posts.collectAsState()
    when (val state = postsState) {
        is BaseState.Failed -> {
            when (state.error) {
                is ApiFailure.Unknown -> CenterErrorText(msg = state.error.error)
            }
        }

        BaseState.Loading -> CenterCircularProgressBar()
        is BaseState.Success -> {
            Box {
                var searchData by rememberSaveable {
                    mutableStateOf(emptyList<Post>())
                }
                var query by rememberSaveable {
                    mutableStateOf("")
                }
                LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Adaptive(125.dp)) {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        SearchBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            query = query,
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.Close, contentDescription = "Clear",
                                    modifier = Modifier.clickable {
                                        query = ""
                                    }
                                )
                            },
                            onQueryChange = { q ->
                                val d = searchData.filter {
                                    it.caption.lowercase().contains(q.lowercase())
                                }
                                searchData = d
                                query = q
                            },
                            placeholder = {
                                Text(
                                    stringResource(id = R.string.search_name),
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            },
                            onSearch = {},
                            active = false,
                            onActiveChange = {}
                        ) {}
                    }
                    if (searchData.isEmpty() || query.isEmpty()) {
                        searchData = state.data
                    } else {
                        searchData.map { it.caption.lowercase() }.contains(query.lowercase())
                    }
                    items(searchData) {
                        AsyncImage(
                            it.postImage,
                            contentDescription = it.caption,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .border(
                                    1.dp,
                                    color = MaterialTheme.colorScheme.background
                                )
                                .clickable {
                                    navController.navigate(
                                        "${NavigationItem.ViewPost.route}/${it.id}"
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val homeViewModel: HomeViewModel = viewModel()

            SearchScreen(
                homeViewModel,
                navController = rememberNavController()
            )
        }
    }
}
