package com.example.githubbrowser.presentation.repoFragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.githubbrowser.databinding.RepoContentItemBinding
import com.example.githubbrowser.domain.entity.RepositoryStructureEntry
import javax.inject.Inject

class RepoListAdapter @Inject constructor(): ListAdapter<RepositoryStructureEntry, RepoItemViewHolder>(RepoItemDiffCallback) {

    var onClickListener: ((RepositoryStructureEntry) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        val view = RepoContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.root.setOnClickListener{
            onClickListener?.invoke(item)
        }
        holder.bind(item)
    }
}