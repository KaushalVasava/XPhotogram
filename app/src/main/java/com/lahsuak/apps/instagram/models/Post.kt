package com.lahsuak.apps.instagram.models

import androidx.annotation.DrawableRes

data class Post(
    val id: String,
    val userId: String,
    @DrawableRes
    val postImage: Int,
    val caption: String,
    val likeCount: Int = 0
)
