package org.example.project.domain.model


data class User(
    val fullName: String,
    val email: String,
    val phone: String,
    val city: String,
    val country: String,
    val pictureUrl: String,
    val id: Int,

)
