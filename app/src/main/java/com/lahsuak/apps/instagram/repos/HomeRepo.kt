package com.lahsuak.apps.instagram.repos

import com.lahsuak.apps.instagram.api.InstagramApi
import com.lahsuak.apps.instagram.models.BaseResponse
import com.lahsuak.apps.instagram.models.Notification
import com.lahsuak.apps.instagram.models.Post
import com.lahsuak.apps.instagram.models.Story
import com.lahsuak.apps.instagram.models.User
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class HomeRepo @Inject constructor (
    private var instagramApi: InstagramApi,
) {
    suspend fun getUserResponse(): BaseResponse<User> {
        return instagramApi.getUsers()
    }

    suspend fun getPosts(): List<Post> {
        return instagramApi.getPosts().data
    }

    suspend fun getStories(): List<Story> {
        return instagramApi.getStories().data
    }

    suspend fun getNotifications(): List<Notification> {
        return instagramApi.getNotifications().data
    }
}