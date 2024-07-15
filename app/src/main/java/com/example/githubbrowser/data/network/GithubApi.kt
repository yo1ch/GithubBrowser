package com.example.githubbrowser.data.network

import com.example.githubbrowser.data.dto.PaginationResult
import com.example.githubbrowser.data.dto.RepositoryStructureDto
import com.example.githubbrowser.data.dto.RepositoryDto
import com.example.githubbrowser.data.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi{

    @GET("search/users")
    @Headers(GITHUB_HEADER)
    suspend fun getUsers(
        @Query(PAGE_PARAM) page: Int,
        @Query(SIZE_PARAM) perPage: Int,
        @Query(QUERY_PARAM) query: String,
    ): PaginationResult<UserDto>

    @GET("search/repositories")
    @Headers(GITHUB_HEADER)
    suspend fun getRepositories(
        @Query(PAGE_PARAM) page: Int,
        @Query(SIZE_PARAM) perPage: Int,
        @Query(QUERY_PARAM) query: String,
    ): PaginationResult<RepositoryDto>

    @GET("repos/{$OWNER_PATH}/{$REPO_PATH}/contents{$PATH_PATH}")
    @Headers(GITHUB_OBJECT_HEADER)
    suspend fun getRepositoryStructure(
        @Path(OWNER_PATH) owner: String,
        @Path(REPO_PATH) repo: String,
        @Path(PATH_PATH) path: String,
    ): RepositoryStructureDto

}
private const val GITHUB_OBJECT_HEADER = "Accept:application/vnd.github.object+json X-GitHub-Api-Version:2022-11-28"
private const val GITHUB_HEADER = "Accept:application/vnd.github.object+json X-GitHub-Api-Version:2022-11-28"
private const val OWNER_PATH = "owner"
private const val REPO_PATH = "repo"
private const val PATH_PATH = "path"
private const val PAGE_PARAM = "page"
private const val SIZE_PARAM = "per_page"
private const val QUERY_PARAM = "q"
