package com.example.githubbrowser.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubbrowser.data.network.GithubApi
import com.example.githubbrowser.data.toModel
import com.example.githubbrowser.domain.entity.SearchResult
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(
    private val api: GithubApi,
    private val query: String
) : PagingSource<Int, SearchResult>() {
    override fun getRefreshKey(state: PagingState<Int, SearchResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResult> {
        val page = params.key ?: FIRST_PAGE
        val size = params.loadSize
        return try {
            val reposItems = api.getRepositories(page, size, query).items
                .map { it.toModel() }
                .sortedBy { it.name }
            val usersItems = api.getUsers(page, size, query).items
                .sortedBy { it.login }
                .map { it.toModel() }
            val resultList = reposItems + usersItems

            LoadResult.Page(
                data = resultList,
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (resultList.isNotEmpty()) page + 1 else null
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val FIRST_PAGE = 0
    }
}