package ru.cryhards.brootkiddie.engine.android.templates

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ScrollView
import ru.cryhards.brootkiddie.R

open class InnerApplicationActivity : FullScreenActivity() {
    @Suppress("MemberVisibilityCanPrivate")
    lateinit var mainFrame: FrameLayout
    @Suppress("MemberVisibilityCanPrivate")
    lateinit var sideBar: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inner_application_template)

        mainFrame = findViewById(R.id.mainFrame)
        sideBar = findViewById(R.id.sideBar)
    }
}
