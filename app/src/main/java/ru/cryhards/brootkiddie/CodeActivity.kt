package ru.cryhards.brootkiddie

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.cryhards.brootkiddie.engine.android.templates.FullScreenActivity

class CodeActivity : FullScreenActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)
    }
}
