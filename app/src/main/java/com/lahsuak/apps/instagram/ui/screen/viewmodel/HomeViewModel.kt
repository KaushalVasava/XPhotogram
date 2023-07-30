package com.lahsuak.apps.instagram.ui.screen.viewmodel

import androidx.lifecycle.ViewModel
import com.lahsuak.apps.instagram.models.Post
import com.lahsuak.apps.instagram.models.Story
import com.lahsuak.apps.instagram.models.User
import com.lahsuak.apps.instagram.repos.HomeRepo

class HomeViewModel : ViewModel() {
    private val homeRepo: HomeRepo by lazy {
        HomeRepo()
    }
    val users = homeRepo.getUser()
    val posts = homeRepo.getPosts()
    val stories = homeRepo.getStories()
    val notifications = homeRepo.getNotifications()
    fun getUsersByIds(userIds: List<String>): List<User> {
        return users.filter { user ->
            userIds.any {
                user.id == it
            }
        }
    }

    fun getUserById(id: String): User? {
        return users.find {
            id == it.id
        }
    }

    fun getPosts(ids: List<String>): List<Post> {
        return posts.filter { post ->
            ids.any {
                post.id == it
            }
        }
    }

    fun getPostsByUserIds(userIds: List<String>): List<Post> {
        return posts.filter { post ->
            userIds.any {
                post.userId == it
            }
        }
    }

    fun getStories(ids: List<String>): List<Story> {
        return stories.filter { story ->
            ids.any {
                story.id == it
            }
        }
    }

    fun getStoriesByUserIds(userIds: List<String>): List<Story> {
        return stories.filter { story ->
            userIds.any {
                story.userId == it
            }
        }
    }
}