package com.example.githubbrowser.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryDto(
    val id: Int,
    val name: String,
    @SerialName("forks_count")val forksCount: String,
    val description: String?,
)