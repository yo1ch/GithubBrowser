package com.example.githubbrowser.domain.repository

import com.example.githubbrowser.domain.entity.RepositoryStructure
import com.example.githubbrowser.domain.entity.SearchResult
import retrofit2.http.Path

interface AppRepository {

    suspend fun getSearchResult(page: Int, size: Int, searchQuery: String): List<SearchResult>

    suspend fun getRepositoryStructure(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String,
    ): RepositoryStructure?

}