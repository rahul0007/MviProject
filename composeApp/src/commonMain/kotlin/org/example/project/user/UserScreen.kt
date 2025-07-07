package org.example.project.user

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import androidx.compose.ui.text.style.TextAlign
import org.example.project.user.componet.UserListItem

@Composable
fun UserScreen() {
    val viewModel: UserViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is UserUiEffect.ShowToast -> println(effect.message)
            }
        }
    }
    UserScreenContent(
        state = uiState,
        onRetry = { viewModel.fetchUsers() }
    )
}


@Composable
fun UserScreenContent(
    state: UserUiState,
    onRetry: () -> Unit
) {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is UserUiState.Loading -> CircularProgressIndicator()
                is UserUiState.Success -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.users) { user ->
                        UserListItem(user)
                    }
                }

                is UserUiState.Error -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        state.message,
                        textAlign = TextAlign.Center,
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

