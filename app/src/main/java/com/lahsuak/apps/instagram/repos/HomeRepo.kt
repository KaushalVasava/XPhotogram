package com.lahsuak.apps.instagram.repos

import com.lahsuak.apps.instagram.api.InstagramApi
import com.lahsuak.apps.instagram.models.BaseResponse
import com.lahsuak.apps.instagram.models.Notification
import com.lahsuak.apps.instagram.models.Post
import com.lahsuak.apps.instagram.models.Story
import com.lahsuak.apps.instagram.models.Tweet
import com.lahsuak.apps.instagram.models.User
import com.lahsuak.apps.instagram.util.DemoData
import com.lahsuak.apps.instagram.util.DemoData.tweets
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

@ActivityScoped
class HomeRepo @Inject constructor(
    private var instagramApi: InstagramApi,
) {
    val tweetList = mutableListOf<Tweet>()

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

    suspend fun getTweets(): List<Tweet> {
        delay(5000L)
        for (i in 1..30) {
            tweetList.add(
                Tweet(
                    description = tweets.random(),
                    userId = "",
                    comments = tweets.subList(0, Random.nextInt(1, 4)),
                    likeCount = i * i,
                    retweetCount = i * 2,
                    bookmarkCount = 10 * i,
                    imageUrl = DemoData.urls.subList(0, 2),
                    timeStamp = System.currentTimeMillis()
                )
            )
        }
        return tweetList
    }
}