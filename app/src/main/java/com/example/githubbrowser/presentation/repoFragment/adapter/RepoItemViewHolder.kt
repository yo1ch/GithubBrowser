package com.example.githubbrowser.presentation.repoFragment.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.githubbrowser.R
import com.example.githubbrowser.databinding.RepoContentItemBinding
import com.example.githubbrowser.domain.entity.EntryType
import com.example.githubbrowser.domain.entity.RepositoryStructureEntry

class RepoItemViewHolder(val binding: RepoContentItemBinding) : ViewHolder(binding.root) {

    fun bind(item: RepositoryStructureEntry) {
        bindName(name = item.name)
        bindIcon(entryType = item.type)
    }

    private fun bindName(name: String) {
        binding.entryName.text = name
    }

    private fun bindIcon(entryType: EntryType) {
        val imageRes =
            if (entryType == EntryType.FILE) R.drawable.ic_file else R.drawable.ic_folder
        Glide
            .with(binding.root)
            .load(imageRes)
            .into(binding.entryIcon)
    }

}