package com.lahsuak.apps.instagram.repos

import com.lahsuak.apps.instagram.api.InstagramApi
import com.lahsuak.apps.instagram.models.BaseResponse
import com.lahsuak.apps.instagram.models.Notification
import com.lahsuak.apps.instagram.models.Post
import com.lahsuak.apps.instagram.models.Story
import com.lahsuak.apps.instagram.models.User


class HomeRepo(
    private var instagramApi: InstagramApi,
) {
    suspend fun getUserResponse(): BaseResponse<User> {
        return instagramApi.getUsers()
    }
    suspend fun getPost(): List<Post> {
        return instagramApi.getPosts().data
    }
    suspend fun getStorie(): List<Story> {
        return instagramApi.getStories().data
    }
    suspend fun getNotification(): List<Notification> {
        return instagramApi.getNotifications().data
    }

//    fun getUser(): List<User> {
//        return listOf(
//            User(
//                id = MY_USER_ID,
//                name = "Kaushal",
//                profileImage = R.drawable.mypic,
//                bio = "Android developer | Nature Lover",
//                links = listOf("https://github.com//KaushalVasava"),
//                followerIds = listOf("12346", "12347", "12348", "12349", "12345"),
//                followingIds = listOf("12346", "12348", "12349"),
//                postIds = listOf(
//                    "123464",
//                    "123465",
//                    "123466",
//                    "123467",
//                    "123468",
//                    "123469"
//                ),
//                storyIds = listOf("12411", "12412", "12413", "12429", "12428"),
//            ),
//            User(
//                id = "12345",
//                name = "Kashish",
//                profileImage = R.drawable.nature,
//                bio = "Engineering student",
//                links = listOf("https://github.com//KaushalVasava"),
//                followerIds = listOf("12347", "12348", "12349"),
//                followingIds = listOf("12346", "12348"),
//                postIds = listOf("123452"),
//                storyIds = listOf("12410"),
//            ),
//            User(
//                id = "12346",
//                name = "Jigar",
//                profileImage = R.drawable.imag,
//                bio = "IOS Developer",
//                links = listOf("https://github.com//KaushalVasava"),
//                followerIds = listOf(MY_USER_ID, "12349"),
//                followingIds = listOf(MY_USER_ID, "12348", "12349"),
//                postIds = listOf("123455", "123456", "123458"),
//                storyIds = listOf("12420", "12418", "12419"),
//            ),
//            User(
//                id = "12347",
//                name = "Sachin",
//                profileImage = R.drawable.firebase_logo,
//                bio = "Lawyer | Artist",
//                links = listOf("https://github.com//KaushalVasava"),
//                followerIds = listOf("12347", "12348"),
//                followingIds = listOf("12347"),
//                postIds = emptyList(),
//                storyIds = emptyList(),
//            ),
//            User(
//                id = "12348",
//                name = "Neha",
//                profileImage = R.drawable.androidstudio_logo,
//                bio = "Clothes maker",
//                links = listOf("https://github.com//KaushalVasava"),
//                followerIds = listOf("12347", "12348"),
//                followingIds = listOf(MY_USER_ID, "12348"),
//                postIds = emptyList(),
//                storyIds = emptyList(),
//            ),
//            User(
//                id = "12349",
//                name = "Vijay",
//                profileImage = R.drawable.ic_bubble,
//                bio = "Traveller",
//                links = listOf("https://github.com//KaushalVasava"),
//                followerIds = listOf("12347", "12348"),
//                followingIds = listOf(MY_USER_ID, "12347", "12349"),
//                postIds = listOf("123451", "123452", "123453", "123454"),
//                storyIds = emptyList(),
//            ),
//        )
//    }
//
//    fun getPosts(): List<Post> {
//        return listOf(
//            Post(
//                "123451",
//                MY_USER_ID,
//                postImage = R.drawable.canva_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 8
//            ), Post(
//                "123452",
//                "12349",
//                postImage = R.drawable.androidstudio_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store,sdfjijifidfsfjdsfsfskfsjfsdkfsfkj,dfkssfkfsdkfskfsfjsfksdfjdsjkfkjfskjfkdsjfdskfksjdfjksdfjksdfjskjfsdjkfsdjf," +
//                        "dfskfksdfksfjksfsdfjksfsfkjsfjksfjkfskfskjfksjfjskfskjdfjksfjksdfsdjkfksdfkjsdf",
//                likeCount = 230
//            ), Post(
//                "123453",
//                "12348",
//                postImage = R.drawable.firebase_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 343340
//            ), Post(
//                "123454",
//                "12347",
//                postImage = R.drawable.imag,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 343434
//            ), Post(
//                "123455",
//                "12346",
//                postImage = R.drawable.twitter,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123456",
//                "12346",
//                postImage = R.drawable.canva_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123457",
//                "12346",
//                postImage = R.drawable.firebase_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123458",
//                "12346",
//                postImage = R.drawable.androidstudio_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123459",
//                "12347",
//                postImage = R.drawable.mypic,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123460",
//                "12347",
//                postImage = R.drawable.twitter,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123461",
//                "12348",
//                postImage = R.drawable.flashlight_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123462",
//                "12348",
//                postImage = R.drawable.twitter,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123463",
//                "Kaushal",
//                postImage = R.drawable.ic_videocam,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123464",
//                MY_USER_ID,
//                postImage = R.drawable.canva_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123465",
//                MY_USER_ID,
//                postImage = R.drawable.mypic,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123466",
//                MY_USER_ID,
//                postImage = R.drawable.androidstudio_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123467",
//                MY_USER_ID,
//                postImage = R.drawable.imag,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123468",
//                MY_USER_ID,
//                postImage = R.drawable.canva_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123469",
//                MY_USER_ID,
//                postImage = R.drawable.flashlight_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123470",
//                MY_USER_ID,
//                postImage = R.drawable.twitter,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123471",
//                MY_USER_ID,
//                postImage = R.drawable.ic_videocam,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123472",
//                MY_USER_ID,
//                postImage = R.drawable.ic_bubble,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123473",
//                MY_USER_ID,
//                postImage = R.drawable.ic_music,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123474",
//                MY_USER_ID,
//                postImage = R.drawable.firebase_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123475",
//                MY_USER_ID,
//                postImage = R.drawable.flashlight_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123476",
//                MY_USER_ID,
//                postImage = R.drawable.androidstudio_logo,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123477",
//                MY_USER_ID,
//                postImage = R.drawable.twitter,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            ), Post(
//                "123478",
//                MY_USER_ID,
//                postImage = R.drawable.imag,
//                caption = "I'm so happy bcz I create multiple apps and published in the play store",
//                likeCount = 0
//            )
//        )
//    }
//
//    fun getStories(): List<Story> {
//        return listOf(
//            Story("12431", "12448", image = R.drawable.flashlight_logo, likeCount = 3),
//            Story("12430", "12348", image = R.drawable.canva_logo),
//            Story("12429", MY_USER_ID, image = R.drawable.mypic),
//            Story("12428", MY_USER_ID, image = R.drawable.androidstudio_logo),
//            Story("12427", "12348", image = R.drawable.firebase_logo),
//            Story("12426", "12348", image = R.drawable.flashlight_logo),
//            Story("12425", "12348", image = R.drawable.canva_logo),
//            Story("12410", "12349", image = R.drawable.twitter),
//            Story("12411", MY_USER_ID, image = R.drawable.imag, likeCount = 40),
//            Story("12412", MY_USER_ID, image = R.drawable.androidstudio_logo),
//            Story("12413", MY_USER_ID, image = R.drawable.flashlight_logo),
//            Story("12414", MY_USER_ID, image = R.drawable.canva_logo),
//            Story("12415", "12349", image = R.drawable.firebase_logo),
//            Story("12416", "12349", image = R.drawable.mypic),
//            Story("12417", MY_USER_ID, image = R.drawable.firebase_logo),
//            Story("12418", "12346", image = R.drawable.canva_logo),
//            Story("12419", "12346", image = R.drawable.ic_bubble),
//            Story("12420", "12346", image = R.drawable.ic_music),
//            Story("12421", "12347", image = R.drawable.androidstudio_logo),
//            Story("12422", "12347", image = R.drawable.firebase_logo),
//            Story("12423", "12347", image = R.drawable.imag),
//            Story("12424", "12347", image = R.drawable.flashlight_logo),
//        )
//    }
//
//    fun getNotifications(): List<Notification> {
//        val list = mutableListOf<Notification>()
//        for (i in 0..20) {
//            val images = listOf(
//                R.drawable.nature,
//                R.drawable.androidstudio_logo,
//                R.drawable.imag,
//                R.drawable.androidstudio_logo
//            )
//            list.add(
//                Notification(
//                    id = UUID.randomUUID().toString(),
//                    image = images.random(),
//                    title = "Your post$i liked by Test user$i",
//                    timeDate = System.currentTimeMillis(),
//                    actionBy = "${25 * i} likes"
//                )
//            )
//        }
//        return list
//    }
}