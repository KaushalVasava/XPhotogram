package com.lahsuak.apps.instagram.models

data class BaseResponse<T>(
    val type: String,
    val data: List<T>,
)