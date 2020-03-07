package com.idv.core.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun runOnBackground(action: () -> Unit) {
    GlobalScope.launch(Dispatchers.IO) {
        action.invoke()
    }
}