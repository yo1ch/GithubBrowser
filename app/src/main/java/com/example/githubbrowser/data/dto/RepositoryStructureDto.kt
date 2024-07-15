package com.example.githubbrowser.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RepositoryStructureDto(
    val name: String,
    val path: String,
    val entries: List<RepositoryStructureEntryDto>,
)
