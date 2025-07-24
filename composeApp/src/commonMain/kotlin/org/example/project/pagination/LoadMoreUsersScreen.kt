package org.example.project.pagination
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
import org.example.project.user.UserUiState
import org.example.project.user.component.ShimmerUserCard
import org.example.project.user.component.UserCard
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadMoreUsers() {
    val viewModel: LoadMoreUserViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    var isRefreshing by remember { mutableStateOf(false) } // ✅ Track pull-to-refresh

    Scaffold { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (val state = uiState) {
                is UserUiState.Loading -> {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(15) {
                            ShimmerUserCard()
                        }
                    }

//                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                is UserUiState.Success -> {
                    PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = {
                            isRefreshing = true
                            viewModel.refreshData {
                                isRefreshing = false
                            }
                        }
                    ) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(state.users) { user ->
                                UserCard(user = user)
                            }

                            //  Bottom Loader for pagination
                            if (state.isLoadingMore) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        }
                    }

                    LaunchedEffect(listState) {
                        snapshotFlow {
                            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                        }.collect { lastVisibleItemIndex ->
                            val totalItems = state.users.size
                            if (lastVisibleItemIndex != null &&
                                lastVisibleItemIndex >= totalItems - 1 && // Preload threshold
                                !state.isLoadingMore
                            ) {
                                println("Triggering loadNextPage()")
                                viewModel.loadNextPage()
                            }
                        }
                    }
                }

                is UserUiState.Error -> {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(state.message, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadNextPage() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

