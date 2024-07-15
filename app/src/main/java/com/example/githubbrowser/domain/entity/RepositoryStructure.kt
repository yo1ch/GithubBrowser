package com.example.githubbrowser.domain.entity


data class RepositoryStructure(
    val name: String,
    val path: String,
    val entries: List<RepositoryStructureEntry>,
)
