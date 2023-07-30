package com.lahsuak.apps.instagram.ui.screen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lahsuak.apps.instagram.models.Notification
import com.lahsuak.apps.instagram.models.Post
import com.lahsuak.apps.instagram.models.Story
import com.lahsuak.apps.instagram.models.User
import com.lahsuak.apps.instagram.repos.HomeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepo,
) : ViewModel() {
    val users = MutableStateFlow<List<User>>(emptyList())
    val posts = MutableStateFlow<List<Post>>(emptyList())
    val stories = MutableStateFlow<List<Story>>(emptyList())
    val notifications = MutableStateFlow<List<Notification>>(emptyList())

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            try {
                val response = homeRepo.getUserResponse()
                if (response.type == "success_user") {
                    val user = response.data
                    getPosts()
                    getStories()
                    getNotifications()
                    Log.d("TAG", "getUser: ${user.size}")
                    users.value = user
                } else {
                    Log.d("TAG", "data: Failed")
                }
            } catch (e: Exception) {
                Log.d("TAG", "data: ${e.message}")
            }

        }
    }

    fun getPosts() {
        viewModelScope.launch {
            try {
                val post = homeRepo.getPost()
                Log.d("TAG", "getPosts: ${post.size}")
                posts.value = post
            } catch (e: Exception) {
                Log.d("TAG", "data: ${e.message}")
                posts.value = emptyList()
            }
        }
    }

    fun getStories() {
        viewModelScope.launch {
            try {
                val story = homeRepo.getStorie()
                Log.d("TAG", "getStory: ${story.size}")
                stories.value = story
            } catch (e: Exception) {
                Log.d("TAG", "data: ${e.message}")
                stories.value = emptyList()
            }
        }
    }

    fun getNotifications() {
        viewModelScope.launch {
            try {
                notifications.value = homeRepo.getNotification()
            } catch (e: Exception) {
                Log.d("TAG", "data: ${e.message}")
                notifications.value = emptyList()
            }
        }
    }

    fun getUsersByIds(userIds: List<String>): List<User> {
        return users.value.filter { user ->
            userIds.any {
                user.id == it
            }
        }
    }

    fun getUserById(id: String): User? {
        Log.d("TAG", "getUserById: $id and ${users.value.size}")
        return users.value.find {
            id == it.id
        }
    }

    fun getPosts(ids: List<String>): List<Post> {
        return posts.value.filter { post ->
            ids.any {
                post.id == it
            }
        }
    }

    fun getPostsByUserIds(userIds: List<String>): List<Post> {
        return posts.value.filter { post ->
            userIds.any {
                post.userId == it
            }
        }
    }

    fun getStories(ids: List<String>): List<Story> {
        return stories.value.filter { story ->
            ids.any {
                story.id == it
            }
        }
    }

    fun getStoriesByUserIds(userIds: List<String>): List<Story> {
        return stories.value.filter { story ->
            userIds.any {
                story.userId == it
            }
        }
    }
}