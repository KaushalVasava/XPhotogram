package com.lahsuak.apps.instagram.ui.components

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lahsuak.apps.instagram.ui.theme.JetPackComposeBasicTheme

@Composable
fun ToggleIconButton(
    modifier: Modifier = Modifier,
    enableTint: Color = Color.Red,
    disableTint: Color = MaterialTheme.colorScheme.onBackground,
    enableIcon: Painter,
    disableIcon: Painter,
    initialState: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    IconToggleButton(
        checked = initialState,
        onCheckedChange = onCheckedChange,
        modifier = modifier
    ) {
        val transition = updateTransition(initialState, label = "favorite")
        val tint by animateColorAsState(
            targetValue = if (initialState) enableTint else disableTint,
            label = "tint",
        )

        // om below line we are specifying transition
        val size by transition.animateDp(
            transitionSpec = {
                // on below line we are specifying transition
                if (false isTransitioningTo true) {
                    // on below line we are specifying key frames
                    keyframes {
                        // on below line we are specifying animation duration
                        durationMillis = 1000
                        // on below line we are specifying animations.
                        30.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                        35.dp at 15 with FastOutLinearInEasing // for 15-75 ms
                        40.dp at 75 // ms
                        35.dp at 150 // ms
                    }
                } else {
                    spring(stiffness = Spring.StiffnessVeryLow)
                }
            },
            label = "Size"
        ) {
            if (it)
                30.dp
            else
                30.dp
        }
        Icon(
            tint = tint,
            painter = if (initialState) {
                enableIcon
            } else {
                disableIcon
            },
            contentDescription = null,
            modifier = modifier.size(size)
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ButtonPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            ToggleIconButton(
                enableIcon =
                rememberVectorPainter(image = Icons.Default.Favorite),
                disableIcon =
                rememberVectorPainter(image = Icons.Default.FavoriteBorder),
                initialState = false
            ){}
        }
    }
}