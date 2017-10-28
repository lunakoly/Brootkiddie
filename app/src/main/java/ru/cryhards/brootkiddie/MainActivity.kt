package ru.cryhards.brootkiddie

import android.os.Bundle
import android.widget.TextView
import ru.cryhards.brootkiddie.templates.InnerApplicationActivity

class MainActivity : InnerApplicationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainFrame.addView(TextView(this))
        sideBar.addView(TextView(this))
    }
}
