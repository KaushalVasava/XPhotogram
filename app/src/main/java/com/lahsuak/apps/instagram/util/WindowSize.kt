package com.lahsuak.apps.instagram.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration


enum class WindowType {
    Compat, Medium, Expanded
}

data class WindowSize(
    val width: WindowType,
    val height: WindowType
)

@Composable
fun rememberWindowSize(): WindowSize {
    val configuration = LocalConfiguration.current
    val screenWidth by remember(key1 = configuration) {
        mutableIntStateOf(configuration.screenWidthDp)
    }
    val screenHeight by remember(key1 = configuration) {
        mutableIntStateOf(configuration.screenHeightDp)
    }
    return WindowSize(
        width = getScreenWidth(screenWidth),
        height = getScreenHeight(screenHeight)
    )
}

fun getScreenWidth(width: Int): WindowType = when {
    width < 600 -> WindowType.Compat
    width < 840 -> WindowType.Medium
    else -> WindowType.Expanded
}

fun getScreenHeight(height: Int): WindowType = when {
    height < 600 -> WindowType.Compat
    height < 840 -> WindowType.Medium
    else -> WindowType.Expanded
}