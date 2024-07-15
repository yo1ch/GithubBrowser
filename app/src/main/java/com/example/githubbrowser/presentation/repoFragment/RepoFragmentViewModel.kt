package com.example.githubbrowser.presentation.repoFragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubbrowser.domain.entity.RepositoryStackItem
import com.example.githubbrowser.domain.entity.RepositoryStructure
import com.example.githubbrowser.domain.repository.AppRepository
import com.example.githubbrowser.domain.usecase.FetchRepositoryStructureUseCase
import com.example.githubbrowser.presentation.utils.ResourceState
import com.example.githubbrowser.presentation.utils.arrayDequeOf
import com.example.githubbrowser.presentation.utils.pop
import com.example.githubbrowser.presentation.utils.push
import com.example.githubbrowser.presentation.utils.replaceLast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoFragmentViewModel @Inject constructor(
    private val repository: AppRepository,
    private val fetchRepositoryStructureUseCase: FetchRepositoryStructureUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args = RepoFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _repositoryStructure =
        MutableStateFlow<ResourceState<RepositoryStructure>>(ResourceState.Loading)
    val repositoryStructure = _repositoryStructure.asStateFlow()

    private val _shouldPopBackStack = MutableStateFlow<Boolean>(false)
    val shouldPopBackStack = _shouldPopBackStack.asStateFlow()

    private val currentStack: ArrayDeque<RepositoryStackItem> = arrayDequeOf()

    init {
        fetchRepositoryStructure()
    }

    fun fetchRepositoryStructure(path: String = "") {
        viewModelScope.launch {
            repository.getRepositoryStructure(owner = args.owner, repo = args.repo, path = path)
                .onSuccess { repoStructure ->
                    _repositoryStructure.value = ResourceState.Content(content = repoStructure)
                    currentStack.push(
                        RepositoryStackItem(
                            path = path,
                            repositoryStructure = ResourceState.Content(content = repoStructure),
                        )
                    )
                }
                .onFailure { error ->
                    _repositoryStructure.value = ResourceState.Error(message = error.toString())
                    currentStack.push(
                        RepositoryStackItem(
                            path = path,
                            repositoryStructure = ResourceState.Error(message = error.toString()),
                        )
                    )
                }
        }
    }

    fun onBackClicked() {
        currentStack.pop()
        currentStack.lastOrNull()?.let {
            _repositoryStructure.value = it.repositoryStructure
        } ?: popFragmentBackstack()
    }

    fun retryRequest() {
        viewModelScope.launch {
            currentStack.lastOrNull()?.let { stackItem ->
                fetchRepositoryStructureUseCase(
                    owner = args.owner,
                    repo = args.repo,
                    path = stackItem.path
                )
                    .onSuccess { repoStructure ->
                        _repositoryStructure.value = ResourceState.Content(content = repoStructure)
                        currentStack.replaceLast(
                            RepositoryStackItem(
                                path = stackItem.path,
                                repositoryStructure = ResourceState.Content(content = repoStructure),
                            )
                        )
                    }
                    .onFailure { error ->
                        _repositoryStructure.value = ResourceState.Error(message = error.toString())
                    }
            }

        }

    }

    private fun popFragmentBackstack() {
        _shouldPopBackStack.value = true
    }
}