package org.example.project.user


sealed class UserUiEffect {
    data class ShowToast(val message: String) : UserUiEffect()
}