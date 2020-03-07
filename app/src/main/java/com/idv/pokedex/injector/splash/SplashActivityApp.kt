package com.idv.pokedex.injector.splash

import com.idv.core.contrats.SplashNavigator
import com.idv.splash.view.SplashActivity
import java.lang.ref.WeakReference

class SplashActivityApp : SplashActivity() {
    override val navigator: SplashNavigator
        get() = SplashNavigatorImpl(WeakReference(this))
}