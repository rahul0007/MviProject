package org.example.project.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.project.data.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _uiEffect = MutableSharedFlow<UserUiEffect>()
    val uiEffect: SharedFlow<UserUiEffect> = _uiEffect.asSharedFlow()
    fun onEvent(event: UserUiEvent) {
        when (event) {
            UserUiEvent.FetchRandomUser -> fetchUsers()
        }
    }

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState
    fun fetchUsers() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                val users = repository.getRandomUsers()
                _uiState.value = UserUiState.Success(users)
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error(e.message ?: "Something went wrong")
            }
        }
    }
}
