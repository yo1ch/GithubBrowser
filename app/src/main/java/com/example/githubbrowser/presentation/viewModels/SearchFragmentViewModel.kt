package com.example.githubbrowser.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubbrowser.domain.entity.SearchResult
import com.example.githubbrowser.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val repository: AppRepository
): ViewModel() {

    private val _searchResult = MutableStateFlow<List<SearchResult>?>(null)
    val searchResult = _searchResult.asStateFlow()

    fun getSearchData(searchQuery: String){
        viewModelScope.launch {
            _searchResult.value = repository.getSearchResult(page = 1, size = 10, searchQuery = searchQuery)
        }
    }

}