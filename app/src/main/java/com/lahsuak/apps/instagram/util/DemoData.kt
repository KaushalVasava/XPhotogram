package com.lahsuak.apps.instagram.util

import com.lahsuak.apps.instagram.models.Notification
import com.lahsuak.apps.instagram.models.Post
import com.lahsuak.apps.instagram.models.Story
import com.lahsuak.apps.instagram.models.User
import com.lahsuak.apps.instagram.util.AppConstants.MY_USER_ID
import java.util.UUID
import kotlin.random.Random

object DemoData {
    val urls = listOf(
        "https://3.bp.blogspot.com/-VVp3WvJvl84/X0Vu6EjYqDI/AAAAAAAAPjU/ZOMKiUlgfg8ok8DY8Hc-ocOvGdB0z86AgCLcBGAsYHQ/s1600/jetpack%2Bcompose%2Bicon_RGB.png",
        "https://vectorportal.com/storage/bD8Fzwwh5EDWD8YJkJfCCQCwQ8pxMUBIQaCbmKaZ.jpg",
        "https://vectorportal.com/storage/anime-avatar.jpg",
        "https://vectorportal.com/storage/aKJ32lYqZ7wSaC2f0NIMZEUh4hhjlVETzKZ3FjyR.jpg",
        "https://vectorportal.com/storage/UxFA72dcdu4df3y1hyEpAUYbpqSecbrhnjck3x77.jpg",
        "https://vectorportal.com/storage/Eg0cerMrth5t1FDREtUJQr8RmvmXXvA9XEsL7tcH.jpg",
        "https://cdn.pixabay.com/photo/2023/05/28/03/34/flowers-8022731_1280.jpg",
        "https://cdn.pixabay.com/photo/2023/07/14/10/50/flower-8126748_1280.jpg",
    )
    val tweets = listOf(
        "I'm so happy, I build app using Jetpack compose, You can build complex ui with less code and you make it fun using animations",
        "UI elements are hierarchical, with elements contained in other elements. In Compose, you build a UI hierarchy by calling composable functions from other composable functions.",
        "Lists and animations are everywhere in apps. In this lesson, you will learn how Compose makes it easy to create lists and fun to add animations",
        "The Navigation component provides support for Jetpack Compose applications. You can navigate between composable while taking advantage of the Navigation component’s infrastructure and features.",
        "Jetpack compose for UI development\n" +
                "Kotlin for programming\n" +
                "MVVM architecture\n" +
                "Coil library for dynamic Image loading\n" +
                "Retrofit for REST API data consuming\n" +
                "Jetpack compose Navigation\n" +
                "Lazy list, Card, Other composable functions"
    )
    val demoPost = Post(
        id = "123451",
        userId = "12340",
        postImage = urls.random(),
        caption = "I'm so happy, I build app using Jetpack compose, You can build complex ui with less code and you make it fun using animations",
        likeCount = Random.nextInt(0, 100)
    )
    val demoUser = User(
        id = MY_USER_ID,
        name = "Kaushal",
        profileImage = urls.random(),
        bio = "Android developer | Nature Lover",
        links = listOf("https://github.com//KaushalVasava"),
        followerIds = listOf("12341", "12342", "12343", "12344", "12345", "12346", "12347"),
        followingIds = listOf("12342", "12345", "12344", "12346", "12347"),
        postIds = listOf(
            "123451",
            "123452",
            "123453",
            "123454",
            "123455",
        ),
        storyIds = listOf("1234561", "1234562", "1234563", "1234564", "1234565"),
    )
    val demoStory = Story(
        id = "1234561",
        userId = "12341",
        image =
        "https://vectorportal.com/storage/aKJ32lYqZ7wSaC2f0NIMZEUh4hhjlVETzKZ3FjyR.jpg",
        name = "Car ",
        likeCount = Random.nextInt(0, 100)
    )

    val demoNotification = Notification(
        id = UUID.randomUUID().toString(),
        image = urls.random(),
        title = "Your post liked by Test user",
        timeDate = System.currentTimeMillis(),
        actionBy = "25 likes"
    )
}