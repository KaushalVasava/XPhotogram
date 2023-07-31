package com.lahsuak.apps.instagram.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CircularImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    imageSize: Dp = 75.dp,
    isBorderVisible: Boolean = false,
    isNameVisible: Boolean = false,
    name: String? = null,
    isAnimated: Boolean = false,
    contentScale: ContentScale = ContentScale.Crop,
    width: Dp = 3.dp,
) {
    val borderWidth by remember(key1 = isBorderVisible) {
        mutableStateOf(if (isBorderVisible) width else 0.dp)
    }
    val brush = if (isBorderVisible) {
        Brush.linearGradient(listOf(Color.Yellow, Color.Magenta))
    } else {
        Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val alpha = if (isAnimated) {
            val infiniteTransition = rememberInfiniteTransition(label = "image")
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1000
                        0.7f at 500
                        0.9f at 800
                    },
                    repeatMode = RepeatMode.Reverse
                ), label = "alpha"
            )
            alpha
        } else {
            1f
        }
        AsyncImage(
            imageUrl,
            contentDescription = null,
            contentScale = contentScale,
            modifier = modifier
                .alpha(alpha)
                .size(imageSize)
                .clip(CircleShape)
                .border(
                    width = borderWidth,
                    shape = CircleShape,
                    brush = brush
                )
                .align(Alignment.CenterHorizontally),
        )
        if (isNameVisible && name.isNullOrEmpty().not()) {
            Text(
                name!!,
                style = TextStyle(fontSize = 12.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}



