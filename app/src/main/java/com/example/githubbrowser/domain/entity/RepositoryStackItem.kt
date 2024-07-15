package com.example.githubbrowser.domain.entity

import com.example.githubbrowser.presentation.utils.ResourceState

data class RepositoryStackItem(
    val path: String,
    val repositoryStructure: ResourceState<RepositoryStructure>,
)
