package com.lahsuak.apps.instagram.ui.screen

import androidx.compose.runtime.Composable
import com.lahsuak.apps.instagram.util.ImagePicker

@Composable
fun CreatePostScreen() {
    ImagePicker()
//    Box {
//        Column {
//            Image(
//                painter = painterResource(id = R.drawable.mypic),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .aspectRatio(1f),
//            )
//            LazyVerticalGrid(columns = GridCells.Fixed(4)) {
//                items(list.map {
//                    it.postImage
//                }) {
//                    Image(
//                        painter = it,
//                        contentDescription = null,
//                        modifier = Modifier.padding(4.dp)
//                    )
//                }
//            }
//        }
//    }
}