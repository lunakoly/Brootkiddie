package ru.cryhards.brootkiddie

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.View

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystemUi()
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()

        config.hideStatusBar = true
        config.useImmersiveMode = true
        initialize(Core(), config)

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private fun hideSystemUi() {
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        or View.SYSTEM_UI_FLAG_FULLSCREEN
        or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }
}
