package com.example.android.architecture.blueprints.websocketclient.util.fragment

import androidx.fragment.app.Fragment
import com.example.android.architecture.blueprints.websocketclient.util.context.hideKeyboard

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}