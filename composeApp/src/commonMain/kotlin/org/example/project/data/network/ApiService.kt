package org.example.project.data.network

import org.example.project.data.network.model.UserResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ApiService(private val client: HttpClient) {
    suspend fun getRandomUser(): UserResponse {
        return client.get("https://randomuser.me/api/?results=20").body()
    }

    suspend fun getUsers(page: Int, limit: Int): UserResponse {
        println("loadNextPage 88")

        return client.get("https://randomuser.me/api") {
            parameter("page", page)
            parameter("results", limit)
        }.body<UserResponse>()
    }
}
