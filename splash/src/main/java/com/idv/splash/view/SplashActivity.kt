package com.idv.splash.view

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.idv.core.contrats.SplashNavigator
import com.idv.splash.R

abstract class SplashActivity : AppCompatActivity() {

    abstract val navigator: SplashNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            navigator.navigateMain()
            finish()
        }, 3 * SECONDS)
    }

    companion object {
        const val SECONDS: Long = 1000
    }
}
