package ru.cryhards.brootkiddie

import android.os.Bundle
import android.widget.Button
import ru.cryhards.brootkiddie.engine.android.EngineActivity
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3
import java.lang.Math.random

class MainActivity : EngineActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout)

        initSurface(findViewById(R.id.main_surface))
        addScene(MyScene1("intro"))
        addScene(MyScene2("test"))
        startScene("intro")

        val btn = findViewById<Button>(R.id.lolkek)
        btn.setOnClickListener {
            if (registry.activeScene?.sceneName == "intro")
                registry.switchScene("test")
            else {
                registry.switchScene("intro")
                registry.activeScene?.environment?.sunlight = Vec3(random().toFloat(), random().toFloat(), random().toFloat())
            }
        }
    }
}
