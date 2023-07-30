package com.lahsuak.apps.instagram.api

import com.lahsuak.apps.instagram.models.BaseResponse
import com.lahsuak.apps.instagram.models.Notification
import com.lahsuak.apps.instagram.models.Post
import com.lahsuak.apps.instagram.models.Story
import com.lahsuak.apps.instagram.models.User
import retrofit2.http.GET

interface InstagramApi {

    @GET("users")
    suspend fun getUsers(): BaseResponse<User>

    @GET("posts")
    suspend fun getPosts(): BaseResponse<Post>

    @GET("stories")
    suspend fun getStories(): BaseResponse<Story>

    @GET("notifications")
    suspend fun getNotifications(): BaseResponse<Notification>
}


