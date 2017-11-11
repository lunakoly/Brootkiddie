package ru.cryhards.brootkiddie.engine.android.templates

import android.app.Activity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager

/**
 * Created with love by luna_koly on 29.10.2017.
 */
open class FullScreenActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}