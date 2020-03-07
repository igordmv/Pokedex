package com.idv.core.extensions

import android.app.Activity
import android.content.res.Configuration
import com.idv.core.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun Activity.runOnUI(action: () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        if (!isFinishing) action.invoke()
    }
}

fun Activity.isTablet() = resources.getBoolean(R.bool.is_tablet)

fun Activity.isLandScape() =
    resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE