package org.example.project.user

sealed class UserUiEvent {
    object FetchRandomUser : UserUiEvent()
}