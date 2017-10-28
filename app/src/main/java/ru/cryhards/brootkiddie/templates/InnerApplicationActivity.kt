package ru.cryhards.brootkiddie.templates

import android.app.Activity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ScrollView
import ru.cryhards.brootkiddie.R

open class InnerApplicationActivity : Activity() {
    lateinit var mainFrame: FrameLayout
    lateinit var sideBar: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.inner_application_template)

        mainFrame = findViewById(R.id.mainFrame)
        sideBar = findViewById(R.id.sideBar)
    }
}
