package com.idv.pokedex.injector.splash

import android.content.Intent
import com.idv.core.contrats.SplashNavigator
import com.idv.pokedex.view.MainActivity
import java.lang.ref.WeakReference

class SplashNavigatorImpl(private val activityRef: WeakReference<SplashActivityApp>) : SplashNavigator {
    override fun navigateMain() {
        activityRef.get()?.let {
            val intent = Intent(it, MainActivity::class.java)
            it.startActivity(intent)
        }
    }
}