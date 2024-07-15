package com.example.githubbrowser.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryStructureEntryDto(
    val name: String,
    val sha: String,
    val path: String,
    val type: EntryTypeDto,
    @SerialName("html_url")val htmlUrl: String,
)

@Serializable
enum class EntryTypeDto{
    @SerialName("file")FILE,
    @SerialName("dir")DIR,
}

