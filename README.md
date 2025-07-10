This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

# 🧠  UserScreen with Compose MVI & Clean Architecture

This module implements a **User list screen** using **Jetpack Compose** with a **smart UI state pattern** that is clean, scalable, and easy to maintain.  

It follows a **ViewModel-based architecture** with unidirectional data flow and supports clean state management with **sealed classes**.

---

## 📐 Architecture Overview

- **UserScreen**: Root composable that observes `UserUiState` and renders UI.
- **UserViewModel**: Handles events, manages UI state (`UserUiState`) and side effects (`UserUiEffect`).
- **UserUiState**: Represents UI state as a **sealed class**.
- **UserUiEffect**: For one-time events like showing a toast.
- **UserListItem**: Renders each user in a card layout.

---

## 🚀 Features

✅ Clean separation of concerns  
✅ Sealed class for UI states (**Loading**, **Success**, **Error**)  
✅ Supports dependency injection with Koin
✅ Pull-to-refresh support using a custom PullToRefreshBox.
✅ Lazy loading (pagination) when scrolling to the end of the list.
✅ Loading indicator at the center and bottom.
✅ Error handling with retry option.
✅ Clean MVI-style state management with UserUiState.
✅ Designed with Jetpack Compose + Material 3.
✅ Koin integration for ViewModel injection.

---

### 📦 Requirements

- Kotlin 1.9+
- Jetpack Compose (Material3)
- Koin for dependency injection
- Coroutines for asynchronous work
- LazyColumn + LazyListState for pagination

---
## 🗂 Folder Structure

```text
org.example.project.user
│
├── UserScreen.kt          // Root composable: connects ViewModel and UI
├── UserScreenContent.kt   // Main UI rendering based on state
├── UserListItem.kt        // Single user card in the list
├── UserUiState.kt         // Sealed class for UI state (Loading, Success, Error)
├── UserUiEffect.kt        // UI effects for one-time events (toasts, navigation)
└── UserViewModel.kt       // ViewModel for handling events and business logic
```

## 🧠 UI State (Sealed Class)

```kotlin
sealed class UserUiState {
    object Loading : UserUiState()
    data class Success(val users: List<User>) : UserUiState()
    data class Error(val message: String) : UserUiState()
}
```


### 🔄 Pagination Logic
Pagination is triggered when the last visible item in the LazyColumn is scrolled into view:

```kotlin
LaunchedEffect(listState) {
    snapshotFlow {
        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
    }.collect { lastVisibleItemIndex ->
        if (lastVisibleItemIndex == state.users.lastIndex && !state.isLoadingMore) {
            viewModel.loadNextPage()
        }
    }
}
```


### 🪝 Pull-to-Refresh
- The PullToRefreshBox wraps the list to support swipe-to-refresh:
```kotlin
PullToRefreshBox(
    isRefreshing = isRefreshing,
    onRefresh = {
        isRefreshing = true
        viewModel.refreshData {
            isRefreshing = false
        }
    }
) { ... }
```
---

✅ Eliminates multiple flags (isLoading, error, etc.)

✅ UI is mutually exclusive and easy to reason about.

```


## 📸 Screenshots

![Screenshot_20250708_100336](https://github.com/rahul0007/MviProject/blob/3c8a98659f5fcb4cf130c6938aca14dc773ed3c8/Screenshot_20250708_100336.png).


----


![PullToRefresh](https://github.com/rahul0007/MviProject/blob/3313550eb440b272ea37e2d1881ab32dffaf432f/PullToRefresh.png).


------


![loadMore](https://github.com/rahul0007/MviProject/blob/3313550eb440b272ea37e2d1881ab32dffaf432f/loadMore.png).



