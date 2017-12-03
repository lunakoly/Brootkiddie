package ru.cryhards.brootkiddie

import android.os.Bundle
import ru.cryhards.brootkiddie.engine.android.EngineActivity

class MainActivity : EngineActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout)

        initSurface(findViewById(R.id.main_surface))
//        registry.setTask(MyTask1())
        registry.setTask(MyTask2())
    }
}
