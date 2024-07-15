package com.example.githubbrowser.data.repository

import com.example.githubbrowser.data.network.GithubApi
import com.example.githubbrowser.data.toModel
import com.example.githubbrowser.domain.entity.RepositoryStructure
import com.example.githubbrowser.domain.entity.SearchResult
import com.example.githubbrowser.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi
): AppRepository {
    private suspend fun getUsers(page: Int, size: Int, searchQuery: String): List<SearchResult.User> {
        val result = githubApi.getUsers(page, size, searchQuery)
        return if(result.isSuccessful) {
            result.body()?.items?.map { it.toModel() } ?: emptyList()
        }  else emptyList()
    }

    private suspend fun getRepositories(page: Int, size: Int, searchQuery: String): List<SearchResult.Repository> {
        val result = githubApi.getRepositories(page, size, searchQuery)
        return if(result.isSuccessful) {
            result.body()?.items?.map { it.toModel() } ?: emptyList()
        }  else emptyList()
    }

    override suspend fun getSearchResult(page: Int, size: Int, searchQuery: String): List<SearchResult> {
        val repositories = getRepositories(page, size, searchQuery)
        val users = getUsers(page, size, searchQuery)
        return  repositories + users
    }

    override suspend fun getRepositoryStructure(
        owner: String,
        repo: String,
        path: String
    ): RepositoryStructure? {
        val result = githubApi.getRepositoryStructure(owner, repo, path)
        return if(result.isSuccessful) {
            result.body()?.toModel()
        }  else null
    }

}