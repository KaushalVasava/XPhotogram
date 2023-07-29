package com.lahsuak.apps.instagram.util

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun ImagePicker() {
    var imageUris by remember { mutableStateOf<List<Uri?>>(emptyList()) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uri: List<Uri?> -> imageUris = uri }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(imageUris) {
            if (it != null) {
                AsyncImage(
                    model = it,
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
    LaunchedEffect(key1 = true) {
        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
    }
}