package org.example.project.user

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.user.component.UserListItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserScreen() {
    val viewModel: UserListViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is UserUiEffect.ShowToast -> println(effect.message)
            }
        }
    }

    UserScreenContent(
        state = uiState, onRetry = { viewModel.fetchUsers() })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreenContent(
    state: UserUiState,
    onRetry: () -> Unit
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()

    // Stop refreshing after API call finishes
    LaunchedEffect(state) {
        if (state is UserUiState.Success || state is UserUiState.Error) {
            println("Stopping refresh after API call")
            isRefreshing = false
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (state) {
                is UserUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is UserUiState.Success -> {
                    PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = {
                            println("PullToRefresh triggered")
                            isRefreshing = true
                            onRetry() // Trigger your API call
                        }
                    ) {
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.users, key = { user -> user.email }) { user ->
                                // Animate item appearance
                                AnimatedVisibility(
                                    visible = true, // Always true for now
                                    enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                                    exit = fadeOut() + slideOutVertically(targetOffsetY = { it }),
                                ) {
                                    UserListItem(
                                        user = user,
                                        modifier = Modifier.animateContentSize() // Animate reordering
                                    )
                                }
                            }
                        }

                    }
                }

                is UserUiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            state.message,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = onRetry) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}







