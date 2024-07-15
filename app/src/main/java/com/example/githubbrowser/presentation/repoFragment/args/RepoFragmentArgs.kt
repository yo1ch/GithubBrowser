package com.example.githubbrowser.presentation.repoFragment.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepoFragmentArgs(
    val owner: String,
    val repo: String,
): Parcelable
