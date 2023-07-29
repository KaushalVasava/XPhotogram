package com.lahsuak.apps.instagram.models

import androidx.annotation.DrawableRes

data class Story(
    val id: String,
    val userId: String,
    val name: String? = null,
    @DrawableRes
    val image: Int,
    val likeCount: Int = 0
)
