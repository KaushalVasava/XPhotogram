package com.lahsuak.apps.instagram.models

import androidx.compose.ui.graphics.painter.Painter

data class Chat(
    val senderImage: Painter,
    val receiverImage: Painter,
    val msgs: List<Message>,
)
