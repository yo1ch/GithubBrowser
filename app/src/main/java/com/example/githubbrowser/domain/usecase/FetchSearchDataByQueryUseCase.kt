package com.example.githubbrowser.domain.usecase

import androidx.paging.PagingData
import com.example.githubbrowser.domain.entity.SearchResult
import com.example.githubbrowser.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchSearchDataByQueryUseCase @Inject constructor(
    private val repository: AppRepository
){

    operator fun invoke(query: String): Flow<PagingData<SearchResult>> = repository.getSearchData(query)

}