package com.lahsuak.apps.instagram.models

data class Message(
    val msg: String,
    val timeStamp: String,
    val isSeen: Boolean = false,
    val userId: String,
)
