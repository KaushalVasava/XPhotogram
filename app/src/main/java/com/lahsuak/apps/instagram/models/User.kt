package com.lahsuak.apps.instagram.models

import androidx.annotation.DrawableRes

data class User(
    val id: String,
    val name: String,
    @DrawableRes
    val profileImage: Int,
    val bio: String,
    val links: List<String>,
    val followerIds: List<String>,
    val followingIds: List<String>,
    val postIds: List<String>,
    val storyIds: List<String>
)
