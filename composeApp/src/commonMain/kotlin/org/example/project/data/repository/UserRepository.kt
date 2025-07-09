package org.example.project.data.repository

import org.example.project.data.network.ApiService
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
                pictureUrl = user.picture.large,
                id = 1
            )
        }
    }

    suspend fun fetchUsers(page: Int, limit: Int): List<User> {
        println("loadNextPage 88")
        return apiService.getUsers(page, limit).results.map {
            User(
                fullName = "${it.name.title} ${it.name.first} ${it.name.last}",
                email = it.email,
                phone = it.phone,
                city = "${it.location.city}, ${it.location.country}",
                country = it.location.country,
                pictureUrl = it.picture.large,
                id = 1
            )
        }
    }
}

