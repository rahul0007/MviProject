package org.example.project.domain.usecase

import org.example.project.data.repository.UserRepository
import org.example.project.domain.model.User

class GetUsersUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(page: Int, limit: Int): List<User> {
        println("loadNextPage 77")

        return repository.fetchUsers(page, limit)
    }
}
