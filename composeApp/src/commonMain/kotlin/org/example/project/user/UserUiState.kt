package org.example.project.user

import org.example.project.domain.model.User

sealed class UserUiState {
    object Loading : UserUiState()
    data class Success(val users: List<User>,   val isLoadingMore: Boolean = false) : UserUiState()
    data class Error(val message: String) : UserUiState()
}