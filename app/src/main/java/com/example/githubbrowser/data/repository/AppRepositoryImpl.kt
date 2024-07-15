package com.example.githubbrowser.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubbrowser.data.network.GithubApi
import com.example.githubbrowser.data.pagingSource.SearchPagingSource
import com.example.githubbrowser.data.toModel
import com.example.githubbrowser.domain.entity.RepositoryStructure
import com.example.githubbrowser.domain.entity.SearchResult
import com.example.githubbrowser.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi
): AppRepository {

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

    override fun getSearchData(query: String): Flow<PagingData<SearchResult>>{
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {SearchPagingSource(api = githubApi, query = query)}
        ).flow
    }

    companion object{
        private const val PAGE_SIZE = 10
    }

}