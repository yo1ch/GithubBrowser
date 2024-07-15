package com.example.githubbrowser.domain.repository

import com.example.githubbrowser.domain.entity.SearchResult

interface AppRepository {

    suspend fun getUsers(page: Int, size: Int, searchQuery: String): List<SearchResult.User>

    suspend fun getRepositories(page: Int, size: Int, searchQuery: String): List<SearchResult.Repository>

    suspend fun getSearchResult(page: Int, size: Int, searchQuery: String): List<SearchResult>

}