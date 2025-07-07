package com.example.mvidemo.data.remote.model
import kotlinx.serialization.Serializable
@Serializable
data class UserResponse(
    val results: List<UserResult>,
    val info: Info
)

@Serializable
data class UserResult(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val phone: String,
    val cell: String,
    val picture: Picture
)

@Serializable
data class Name(val title: String, val first: String, val last: String)
@Serializable
data class Location(val city: String, val state: String, val country: String)
@Serializable
data class Picture(val large: String, val medium: String, val thumbnail: String)
@Serializable
data class Info(val seed: String, val results: Int, val page: Int, val version: String)
