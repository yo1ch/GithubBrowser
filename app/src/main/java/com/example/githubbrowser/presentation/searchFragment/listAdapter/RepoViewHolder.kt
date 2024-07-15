package com.example.githubbrowser.presentation.searchFragment.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.githubbrowser.databinding.RepoItemBinding
import com.example.githubbrowser.domain.entity.SearchResult

class RepoViewHolder(val binding: RepoItemBinding) : BaseViewHolder<RepoItemBinding>(binding.root) {

    fun bind(item: SearchResult.Repository) {
        bindDescription(description = item.description)
        bindRepoName(name = item.name)
        bindForksCount(count = item.forksCount)
    }

    private fun bindDescription(description: String) {
        binding.repoDescription.text = description
    }

    private fun bindRepoName(name: String) {
        binding.repoName.text = name
    }

    private fun bindForksCount(count: String) {
        binding.forks.forksCount.text = count
    }

    companion object {
        fun create(view: ViewGroup): RepoViewHolder {
            val inflater = LayoutInflater.from(view.context)
            val binding = RepoItemBinding.inflate(inflater, view, false)
            return RepoViewHolder(binding)
        }
    }

}