package com.example.githubbrowser.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubbrowser.domain.entity.SearchResult
import com.example.githubbrowser.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow<String?>(null)

    val test: Flow<PagingData<SearchResult>> = _searchQuery
        .filterNotNull()
        .flatMapLatest { query ->
            repository.getSearchData(query = query)
        }.cachedIn(viewModelScope)

    fun searchByQuery(searchQuery: String) {
        _searchQuery.value = searchQuery
    }

}