package ru.cryhards.brootkiddie.templates

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ScrollView
import ru.cryhards.brootkiddie.R

open class InnerApplicationActivity : FullScreenActivity() {
    lateinit var mainFrame: FrameLayout
    lateinit var sideBar: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inner_application_template)

        mainFrame = findViewById(R.id.mainFrame)
        sideBar = findViewById(R.id.sideBar)
    }
}
