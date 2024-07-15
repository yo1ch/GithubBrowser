package com.example.githubbrowser.presentation.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

fun Int.dp2px( context: Context): Int{
    val r: Resources = context.resources
    val px = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        r.displayMetrics
    )
    return px.toInt()
}