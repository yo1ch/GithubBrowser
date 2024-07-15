package com.example.githubbrowser.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface SearchResult {

    @Parcelize
    data class Repository(
        val id: Int,
        val name: String,
        val forksCount: String,
        val description: String,
    ): Parcelable, SearchResult

    @Parcelize
    data class User(
        val id: Int,
        val login: String,
        val score: String,
        val avatarUrl: String,
    ) : Parcelable, SearchResult

}