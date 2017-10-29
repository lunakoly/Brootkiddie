package ru.cryhards.brootkiddie

import android.os.Bundle
import android.widget.TextView
import ru.cryhards.brootkiddie.engine.android.EngineActivity
import ru.cryhards.brootkiddie.engine.shapes.RectanglePlane
import ru.cryhards.brootkiddie.engine.shapes.TrianglePlane
import ru.cryhards.brootkiddie.templates.InnerApplicationActivity

class MainActivity : EngineActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registry.setTask(MyTask1())
    }
}
