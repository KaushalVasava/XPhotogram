package com.lahsuak.apps.instagram.models

import java.util.UUID

data class Tweet(
    val id: String = UUID.randomUUID().toString(),
    val description: String,
    val userId: String,
    val comments: List<String> = emptyList(),
    val likeCount: Int = 0,
    val retweetCount: Int = 0,
    val bookmarkCount: Int = 0,
    val imageUrl: List<String> = emptyList(),
    val timeStamp: Long = System.currentTimeMillis()
)
