package com.example.githubbrowser.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RepositoryOwnerDto(
    val id: Int,
    val login: String,
)
