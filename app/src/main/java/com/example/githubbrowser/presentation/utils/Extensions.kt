package com.example.githubbrowser.presentation.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun TextInputEditText.getQueryChangeFlow(): StateFlow<String> {
    val query = MutableStateFlow(String())
    addTextChangedListener(
        object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                query.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}

        }
    )
    return query
}

fun <T> arrayDequeOf(vararg elements: T) = ArrayDeque(elements.toList())

fun <T> ArrayDeque<T>.push(element: T) = addLast(element)

fun <T> ArrayDeque<T>.pop() = removeLastOrNull()
