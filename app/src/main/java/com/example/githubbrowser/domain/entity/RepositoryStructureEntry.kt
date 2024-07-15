package com.example.githubbrowser.domain.entity

data class RepositoryStructureEntry(
    val name: String,
    val path: String,
    val sha: String,
    val type: EntryType,
    val htmlUrl: String,
)

enum class EntryType{
    FILE, DIR,
}
