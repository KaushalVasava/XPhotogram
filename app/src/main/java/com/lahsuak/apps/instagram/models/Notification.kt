package com.lahsuak.apps.instagram.models

import androidx.annotation.DrawableRes

data class Notification(
    val id: String,
    @DrawableRes
    val image: Int,
    val title: String,
    val timeDate: Long,
    val actionBy: String
)
