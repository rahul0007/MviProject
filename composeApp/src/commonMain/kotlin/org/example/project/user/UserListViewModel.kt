package org.example.project.user

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.example.project.data.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserListViewModel(
    private val repository: UserRepository,
) : ViewModel() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val _uiEffect = MutableSharedFlow<UserUiEffect>()
    val uiEffect: SharedFlow<UserUiEffect> = _uiEffect.asSharedFlow()


    fun onEvent(event: UserUiEvent) {
        when (event) {
            UserUiEvent.FetchRandomUser -> fetchUsers()
        }
    }

    init {
        fetchUsers()
    }

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState
    fun fetchUsers() {
        scope.launch {
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
