package com.example.githubbrowser.data

import com.example.githubbrowser.data.dto.RepositoryDto
import com.example.githubbrowser.data.dto.UserDto
import com.example.githubbrowser.domain.entity.SearchResult

fun RepositoryDto.toModel() = SearchResult.Repository(
    id = id, name = name, forksCount = forksCount, description = description ?: String()
)

fun UserDto.toModel() = SearchResult.User(
    id = id, login = login, score = score.toString(), avatarUrl = avatarUrl, htmlUrl = htmlUrl
)