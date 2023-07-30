package com.lahsuak.apps.instagram.models

data class Notification(
    val id: String,
    val image: String,
    val title: String,
    val timeDate: Long,
    val actionBy: String
)
