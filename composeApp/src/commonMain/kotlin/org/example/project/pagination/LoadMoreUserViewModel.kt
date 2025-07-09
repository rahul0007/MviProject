package org.example.project.pagination
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.GetUsersUseCase
import org.example.project.user.UserUiState


class LoadMoreUserViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState

    private var currentPage = 1
    private val pageSize = 20
    private var endReached = false
    private var isNextPageLoading = false

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (isNextPageLoading || endReached) return

        isNextPageLoading = true
        viewModelScope.launch {
            try {
                val currentState = _uiState.value

                //  Show bottom loader
                if (currentState is UserUiState.Success && !currentState.isLoadingMore) {
                    _uiState.value = currentState.copy(isLoadingMore = true)
                    kotlinx.coroutines.yield()
                }

                delay(500)
                val newUsers = getUsersUseCase(currentPage, pageSize)
                if (newUsers.isEmpty()) {
                    endReached = true
                }

                if (currentState is UserUiState.Success) {
                    // Append data & hide loader
                    _uiState.value = currentState.copy(
                        users = currentState.users + newUsers,
                        isLoadingMore = false
                    )
                } else {
                    _uiState.value = UserUiState.Success(
                        users = newUsers,
                        isLoadingMore = false
                    )
                }

                currentPage++
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error(e.message ?: "Something went wrong")
            } finally {
                isNextPageLoading = false
            }
        }
    }

    fun refreshData(onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                currentPage = 1
                endReached = false
                _uiState.value = UserUiState.Loading // Show initial loader
                val newUsers = getUsersUseCase(currentPage, pageSize)
                _uiState.value = UserUiState.Success(
                    users = newUsers,
                    isLoadingMore = false
                )
                currentPage++
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error(e.message ?: "Something went wrong")
            } finally {
                isNextPageLoading = false
                onComplete() // ✅ Stop the swipe-to-refresh indicator
            }
        }
    }


}
