package com.noxinfinity.pdate.data.repository.home

import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.data.models.home.ProfileData
import kotlinx.coroutines.delay

class HomeRepository {
    suspend fun loadMoreProfile() : List<ProfileData> {
        delay(3000)

        return listOf(
            ProfileData(
                name = "Violet",
                age = 24,
                description = "A passionate enthusiast of digital art and anime, eager to share unique creations.",
                imageRes = R.drawable.w1
            ),
            ProfileData(
                name = "Lily",
                age = 22,
                description = "Nature lover who enjoys hiking and exploring the great outdoors.",
                imageRes = R.drawable.w2
            ),
            ProfileData(
                name = "Rose",
                age = 25,
                description = "Chef at a local restaurant, passionate about creating delicious and unique dishes.",
                imageRes = R.drawable.w3
            ),
            ProfileData(
                name = "Jasmine",
                age = 23,
                description = "An avid reader and aspiring author, writing her first fantasy novel.",
                imageRes = R.drawable.w4
            ),
            ProfileData(
                name = "Daisy",
                age = 21,
                description = "A student majoring in environmental science, dedicated to sustainability.",
                imageRes = R.drawable.w5
            ),
            ProfileData(
                name = "Iris",
                age = 26,
                description = "A professional photographer specializing in capturing natural landscapes.",
                imageRes = R.drawable.w6
            ),
            ProfileData(
                name = "Camellia",
                age = 27,
                description = "Yoga instructor with a passion for mindfulness and meditation.",
                imageRes = R.drawable.w7
            ),
            ProfileData(
                name = "Tulip",
                age = 22,
                description = "A software engineer who loves building mobile applications.",
                imageRes = R.drawable.w8
            ),
            ProfileData(
                name = "Lavender",
                age = 28,
                description = "A travel blogger documenting her adventures around the world.",
                imageRes = R.drawable.w9
            ),
            ProfileData(
                name = "Sunflower",
                age = 29,
                description = "A musician and songwriter, performing at local venues.",
                imageRes = R.drawable.w10
            )
        )
    }
}