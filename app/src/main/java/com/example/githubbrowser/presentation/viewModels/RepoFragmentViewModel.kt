package com.example.githubbrowser.presentation.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubbrowser.domain.entity.RepositoryStructure
import com.example.githubbrowser.domain.repository.AppRepository
import com.example.githubbrowser.presentation.repoFragment.RepoFragmentArgs
import com.example.githubbrowser.presentation.utils.arrayDequeOf
import com.example.githubbrowser.presentation.utils.pop
import com.example.githubbrowser.presentation.utils.push
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoFragmentViewModel @Inject constructor(
    private val repository: AppRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args = RepoFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _repositoryStructure = MutableStateFlow<RepositoryStructure?>(null)
    val repositoryStructure = _repositoryStructure.asStateFlow()

    private val _shouldPopBackStack = MutableStateFlow<Boolean>(false)
    val shouldPopBackStack = _shouldPopBackStack.asStateFlow()

    private val currentStack: ArrayDeque<RepositoryStructure?> = arrayDequeOf()

    init {
        fetchRepositoryStructure()
    }

    fun fetchRepositoryStructure(path: String = "") {
        viewModelScope.launch {
            val repo =
                repository.getRepositoryStructure(owner = args.owner, repo = args.repo, path = path)
            _repositoryStructure.value = repo
            currentStack.push(repo)
        }
    }

    fun onBackClicked() {
        currentStack.pop()
        currentStack.lastOrNull()?.let {
            _repositoryStructure.value = it
        } ?: popFragmentBackstack()
    }

    private fun popFragmentBackstack() {
        _shouldPopBackStack.value = true
    }
}