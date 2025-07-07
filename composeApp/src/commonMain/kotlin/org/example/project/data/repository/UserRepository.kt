package org.example.project.data.repository

import org.example.project.data.remote.ApiService
import org.example.project.domain.model.User

class UserRepository(private val apiService: ApiService) {
    suspend fun getRandomUsers(): List<User> {
        val response = apiService.getRandomUser()
        return response.results.map { user ->
            User(
                fullName = "${user.name.title} ${user.name.first} ${user.name.last}",
                email = user.email,
                phone = user.phone,
                city = "${user.location.city}, ${user.location.country}",
                country = user.location.country,
                pictureUrl = user.picture.large
            )
        }
    }
}
