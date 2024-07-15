package com.example.githubbrowser.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val login: String,
    val score: Double,
    @SerialName("avatar_url")val avatarUrl: String,
    @SerialName("html_url")val htmlUrl: String,
)
