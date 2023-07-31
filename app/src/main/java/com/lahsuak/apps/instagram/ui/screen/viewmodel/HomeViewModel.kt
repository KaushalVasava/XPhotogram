package com.lahsuak.apps.instagram.ui.screen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lahsuak.apps.instagram.models.ApiFailure
import com.lahsuak.apps.instagram.models.BaseState
import com.lahsuak.apps.instagram.models.Notification
import com.lahsuak.apps.instagram.models.Post
import com.lahsuak.apps.instagram.models.Story
import com.lahsuak.apps.instagram.models.User
import com.lahsuak.apps.instagram.repos.HomeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepo,
) : ViewModel() {
    private val _users =
        MutableStateFlow<BaseState<List<User>, ApiFailure>>(BaseState.Loading)
    val users: StateFlow<BaseState<List<User>, ApiFailure>>
        get() = _users

    private val _posts =
        MutableStateFlow<BaseState<List<Post>, ApiFailure>>(BaseState.Loading)
    val posts: StateFlow<BaseState<List<Post>, ApiFailure>>
        get() = _posts

    private val _stories =
        MutableStateFlow<BaseState<List<Story>, ApiFailure>>(BaseState.Loading)
    val stories: StateFlow<BaseState<List<Story>, ApiFailure>>
        get() = _stories

    private val _notifications =
        MutableStateFlow<BaseState<List<Notification>, ApiFailure>>(BaseState.Loading)
    val notifications: StateFlow<BaseState<List<Notification>, ApiFailure>>
        get() = _notifications

    init {
        getUsers()
        getPosts()
        getStories()
        getNotifications()
    }

    private fun getUsers() {
        viewModelScope.launch {
            try {
                val response = homeRepo.getUserResponse()
                if (response.type == "success_user") {
                    val user = response.data
                    Log.d("TAG", "getUser: ${user.size}")
                    _users.value = BaseState.Success(user)
                } else {
                    _users.value = BaseState.Failed(ApiFailure.Unknown("Error"))
                }
            } catch (e: Exception) {
                _users.value = BaseState.Failed(ApiFailure.Unknown(e.message.toString()))
            }
        }
    }

    fun getUserById(userId: String): User? {
        return (_users.value as BaseState.Success).data.find {
            it.id == userId
        }
    }

    fun getPostById(postId: String): Post? {
        return (_posts.value as BaseState.Success).data.find {
            it.id == postId
        }
    }

    fun getStoryById(storyId: String): Story? {
        return (_stories.value as BaseState.Success).data.find {
            it.id == storyId
        }
    }

    private fun getPosts() {
        viewModelScope.launch {
            _posts.value = try {
                BaseState.Success(homeRepo.getPosts())
            } catch (e: Exception) {
                BaseState.Failed(ApiFailure.Unknown(e.message.toString()))
            }
        }
    }

    private fun getStories() {
        viewModelScope.launch {
            _stories.value = try {
                BaseState.Success(homeRepo.getStories())
            } catch (e: Exception) {
                BaseState.Failed(ApiFailure.Unknown(e.message.toString()))
            }
        }
    }

    private fun getNotifications() {
        viewModelScope.launch {
            _notifications.value = try {
                BaseState.Success(homeRepo.getNotifications())
            } catch (e: Exception) {
                BaseState.Failed(ApiFailure.Unknown(e.message.toString()))
            }
        }
    }

    fun getUsersByIds(users: List<User>, userIds: List<String>): List<User> {
        return users.filter { user ->
            userIds.any {
                user.id == it
            }
        }
    }

    fun getPosts(ids: List<String>): List<Post> {
        return (_posts.value as BaseState.Success).data.filter { post ->
            ids.any {
                post.id == it
            }
        }
    }

    fun getStories(ids: List<String>): List<Story> {
        return (_stories.value as BaseState.Success).data.filter { story ->
            ids.any {
                story.id == it
            }
        }
    }

    fun getFollowers(user: User): List<User> {
        return (_users.value as BaseState.Success).data.filter {
            user.followerIds.any { id ->
                it.id == id
            }
        }
    }
    fun getFollowings(user: User): List<User> {
        return (_users.value as BaseState.Success).data.filter {
            user.followingIds.any { id ->
                it.id == id
            }
        }
    }
}