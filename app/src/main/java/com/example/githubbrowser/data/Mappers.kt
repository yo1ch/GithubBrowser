package com.example.githubbrowser.data

import com.example.githubbrowser.data.dto.EntryTypeDto
import com.example.githubbrowser.data.dto.RepositoryDto
import com.example.githubbrowser.data.dto.RepositoryStructureDto
import com.example.githubbrowser.data.dto.RepositoryStructureEntryDto
import com.example.githubbrowser.data.dto.UserDto
import com.example.githubbrowser.domain.entity.EntryType
import com.example.githubbrowser.domain.entity.RepositoryStructure
import com.example.githubbrowser.domain.entity.RepositoryStructureEntry
import com.example.githubbrowser.domain.entity.SearchResult

fun RepositoryDto.toModel() = SearchResult.Repository(
    id = id, name = name, forksCount = forksCount, description = description ?: String(), ownerLogin = owner.login,
)

fun UserDto.toModel() = SearchResult.User(
    id = id, login = login, score = score.toString(), avatarUrl = avatarUrl, htmlUrl = htmlUrl,
)

fun RepositoryStructureDto.toModel() = RepositoryStructure(
    name = name,
    path = path,
    entries = entries.map { it.toModel() }.sortedByDescending { it.type },
)

fun RepositoryStructureEntryDto.toModel() = RepositoryStructureEntry(
    name = name,
    path = path,
    sha = sha,
    type = when(type){
        EntryTypeDto.FILE -> EntryType.FILE
        EntryTypeDto.DIR -> EntryType.DIR
    },
    htmlUrl = htmlUrl,
)