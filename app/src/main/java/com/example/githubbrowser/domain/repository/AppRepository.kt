package com.example.githubbrowser.domain.repository

import androidx.paging.PagingData
import com.example.githubbrowser.domain.entity.RepositoryStructure
import com.example.githubbrowser.domain.entity.SearchResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path

interface AppRepository {

    fun getSearchData(query: String): Flow<PagingData<SearchResult>>

    suspend fun getRepositoryStructure(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String,
    ): Result<RepositoryStructure>

}