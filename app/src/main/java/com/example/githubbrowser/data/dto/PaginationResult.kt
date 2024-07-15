package com.example.githubbrowser.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationResult<T>(
    @SerialName("total_count")val totalCount: Int,
    val items: List<T>
)
