package org.example.project.data.remote
import com.example.mvidemo.data.remote.model.UserResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ApiService(private val client: HttpClient) {
    suspend fun getRandomUser(): UserResponse {
        return client.get("https://randomuser.me/api/?results=20").body()
    }
}
