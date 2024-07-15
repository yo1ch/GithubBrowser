package com.example.githubbrowser.presentation.searchFragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.githubbrowser.domain.entity.SearchResult

object DiffUtilCallback : DiffUtil.ItemCallback<SearchResult>() {

    override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
        return compareRepo(oldItem, newItem) ||
                compareUser(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean =
        oldItem == newItem

    private fun compareUser(
        oldItem: SearchResult,
        newItem: SearchResult
    ) = (oldItem is SearchResult.User && newItem is SearchResult.User &&
            oldItem.id == newItem.id)

    private fun compareRepo(
        oldItem: SearchResult,
        newItem: SearchResult
    ) = (oldItem is SearchResult.Repository && newItem is SearchResult.Repository &&
            oldItem.id == newItem.id)

}