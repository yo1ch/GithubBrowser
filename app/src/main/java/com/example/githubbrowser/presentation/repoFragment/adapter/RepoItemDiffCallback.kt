package com.example.githubbrowser.presentation.repoFragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.githubbrowser.domain.entity.RepositoryStructureEntry

object RepoItemDiffCallback : DiffUtil.ItemCallback<RepositoryStructureEntry>() {
    override fun areItemsTheSame(oldItem: RepositoryStructureEntry, newItem: RepositoryStructureEntry): Boolean = oldItem.sha == newItem.sha

    override fun areContentsTheSame(oldItem: RepositoryStructureEntry, newItem: RepositoryStructureEntry): Boolean = oldItem == newItem
}