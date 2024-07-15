package com.example.githubbrowser.presentation.searchFragment.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.githubbrowser.databinding.UserItemBinding
import com.example.githubbrowser.domain.entity.SearchResult
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class UserViewHolder(val binding: UserItemBinding) : BaseViewHolder<UserItemBinding>(binding.root) {

    fun bind(item: SearchResult.User) {
        bindAvatar(imgUrl = item.avatarUrl)
        bindLogin(login = item.login)
        bindScore(score = item.score)
    }

    private fun bindLogin(login: String) {
        binding.userLogin.text = login
    }

    private fun bindScore(score: String) {
        binding.userScore.text = score
    }

    private fun bindAvatar(imgUrl: String) {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(1800)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.6f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()
        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
        Glide
            .with(binding.root)
            .load(imgUrl)
            .placeholder(shimmerDrawable)
            .into(binding.userAvatar)
    }

    companion object {
        fun create(view: ViewGroup): UserViewHolder {
            val inflater = LayoutInflater.from(view.context)
            val binding = UserItemBinding.inflate(inflater, view, false)
            return UserViewHolder(binding)
        }
    }

}