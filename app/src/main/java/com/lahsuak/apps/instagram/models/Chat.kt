package com.lahsuak.apps.instagram.models

import androidx.compose.ui.graphics.painter.Painter

data class Chat(
    val senderImage: String,
    val receiverImage: String,
    val msgs: List<Message>,
)
