package ru.cryhards.brootkiddie

import android.os.Bundle
import ru.cryhards.brootkiddie.engine.android.EngineActivity

class MainActivity : EngineActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registry.setTask(MyTask1())
    }
}
