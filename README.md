This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

# 🧠 Smart UserScreen with Compose & Clean Architecture

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
✅ Retry button on error  
✅ Scalable design: easy to add pagination or pull-to-refresh  
✅ Supports dependency injection with Koin

---

## 🗂 Folder Structure

<pre> org.example.project.user │ ├── UserScreen.kt // Root composable: connects ViewModel and UI ├── UserScreenContent.kt // Main UI rendering based on state ├── UserListItem.kt // Single user card in the list ├── UserUiState.kt // Sealed class for UI state (Loading, Success, Error) ├── UserUiEffect.kt // UI effects for one-time events (toasts, navigation) └── UserViewModel.kt // ViewModel for handling events and business logic </pre>

---

## 🧠 UI State (Sealed Class)

```kotlin
sealed class UserUiState {
    object Loading : UserUiState()
    data class Success(val users: List<User>) : UserUiState()
    data class Error(val message: String) : UserUiState()
}

✅ Eliminates multiple flags (isLoading, error, etc.)

✅ UI is mutually exclusive and easy to reason about.



