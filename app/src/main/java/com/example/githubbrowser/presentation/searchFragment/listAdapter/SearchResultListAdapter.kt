package com.example.githubbrowser.presentation.searchFragment.listAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.githubbrowser.domain.entity.SearchResult
import javax.inject.Inject


class SearchResultListAdapter @Inject constructor() :
    ListAdapter<SearchResult, BaseViewHolder<*>>(DiffUtilCallback) {

    var onClickListener: ((SearchResult) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            ViewType.USER.ordinal -> {
                UserViewHolder.create(parent)
            }

            ViewType.REPOSITORY.ordinal -> {
                RepoViewHolder.create(parent)
            }
            else -> throw UnsupportedOperationException("Unexpected ViewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchResult.User -> ViewType.USER.ordinal
            is SearchResult.Repository -> ViewType.REPOSITORY.ordinal
            null -> throw UnsupportedOperationException("Unexpected ViewType")
        }
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is UserViewHolder -> {
                val item = getItem(position) as SearchResult.User
                holder.bind(item)
                holder.binding.root.setOnClickListener {
                    onClickListener?.invoke(item)
                }
            }
            is RepoViewHolder -> {
                val item = getItem(position) as SearchResult.Repository
                holder.bind(item)
                holder.binding.root.setOnClickListener {
                    onClickListener?.invoke(item)
                }
            }
            else -> throw UnsupportedOperationException("Unexpected ViewHolder")
        }
    }
    private enum class ViewType {
        USER, REPOSITORY
    }
}
