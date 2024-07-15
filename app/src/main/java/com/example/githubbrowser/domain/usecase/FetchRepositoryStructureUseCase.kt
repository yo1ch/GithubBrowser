package com.example.githubbrowser.domain.usecase

import com.example.githubbrowser.domain.entity.RepositoryStructure
import com.example.githubbrowser.domain.repository.AppRepository
import javax.inject.Inject

class FetchRepositoryStructureUseCase @Inject constructor(
    private val repository: AppRepository,
) {

    suspend operator fun invoke(
        owner: String,
        repo: String,
        path: String
    ): Result<RepositoryStructure> = repository.getRepositoryStructure(owner = owner, repo = repo, path = path)

}