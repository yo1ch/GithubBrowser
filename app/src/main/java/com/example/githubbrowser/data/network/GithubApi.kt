package com.example.githubbrowser.data.network

import com.example.githubbrowser.data.dto.PaginationResult
import com.example.githubbrowser.data.dto.RepositoryDto
import com.example.githubbrowser.data.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubApi{

    @GET("search/users")
    @Headers("Accept:application/vnd.github+json X-GitHub-Api-Version:2022-11-28")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("q") query: String,
    ): Response<PaginationResult<UserDto>>

    @GET("search/repositories")
    @Headers("Accept:application/vnd.github+json X-GitHub-Api-Version:2022-11-28")
    suspend fun getRepositories(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("q") query: String,
    ): Response<PaginationResult<RepositoryDto>>

}


