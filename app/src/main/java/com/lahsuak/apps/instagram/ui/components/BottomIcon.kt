package com.lahsuak.apps.instagram.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomIcon(imageVector: ImageVector, onClick: () -> Unit) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = Modifier.clickable { onClick() }
    )
}