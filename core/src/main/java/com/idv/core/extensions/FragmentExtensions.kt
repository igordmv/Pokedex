package com.idv.core.extensions

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.idv.core.R

fun Fragment.runOnUI(action: () -> Unit) {
    activity?.runOnUI(action)
}

fun Fragment.isTablet() = resources.getBoolean(R.bool.is_tablet)

fun Fragment.isLandScape() =
    resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun Fragment.tintStatusBarColor(@ColorRes colorResource: Int) {
    if (context != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        activity?.window?.statusBarColor = ContextCompat.getColor(context as Context, colorResource)
    }
}

fun Fragment.tintStatusBarColor(color: String) {
    if (context != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        activity?.window?.statusBarColor = Color.parseColor(color)
    }
}