package com.example.githubbrowser.presentation.utils

sealed interface ResourceState<out T> {

    data object Loading : ResourceState<Nothing>

    data class Content<T>(val content: T) : ResourceState<T>

    data class Error<T>(val message: String? = null) : ResourceState<T>
}