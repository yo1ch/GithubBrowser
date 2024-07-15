package com.example.githubbrowser.presentation.utils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
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

fun Context.hideKeyboard(view: View?) {
    if (view != null) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    } else {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun <T> arrayDequeOf(vararg elements: T) = ArrayDeque(elements.toList())

fun <T> ArrayDeque<T>.push(element: T) = addLast(element)

fun <T> ArrayDeque<T>.pop() = removeLastOrNull()

fun <T> ArrayDeque<T>.replaceLast(element: T) {
    if (isNotEmpty()) {
        removeLast()
        addLast(element)
    } else {
        throw NoSuchElementException("Stack is empty")
    }
}
