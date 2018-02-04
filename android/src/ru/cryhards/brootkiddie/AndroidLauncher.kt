package ru.cryhards.brootkiddie

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.View

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import ru.cryhards.brootkiddie.cleanup.Core

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystemUi()
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()

        config.hideStatusBar = true
        config.useImmersiveMode = true
//        initialize(ru.cryhards.brootkiddie.ReallyGame(), config)
        initialize(Core(), config)

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    fun hideSystemUi() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }
}
