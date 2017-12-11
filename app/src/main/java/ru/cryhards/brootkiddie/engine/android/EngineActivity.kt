package ru.cryhards.brootkiddie.engine.android

import android.view.MotionEvent
import ru.cryhards.brootkiddie.engine.android.templates.FullScreenActivity
import ru.cryhards.brootkiddie.engine.environment.Scene

/**
 * Created with love by luna_koly on 29.10.2017.
 */
open class EngineActivity : FullScreenActivity() {
    private lateinit var registry: EngineRegistry

    fun initSurface(value: EngineSurface): EngineActivity {
        registry = value.registry
        registry.activity = this
        return this
    }

//    fun initSurface(): EngineActivity {
//        val surface = EngineSurface(this, null)
//        setContentView(surface)
//        registry = surface.registry
//        registry.activity = this
//        return this
//    }

    private val onTouchListeners = ArrayList<(MotionEvent) -> Unit>()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        onTouchListeners.forEach { it.invoke(event) }
        registry.activeScene?.activeCamera?.onTouchEvent(event)
        return true
    }

//    fun addOnTouchListener(listener: (MotionEvent) -> Unit): EngineActivity {
//        onTouchListeners.add(listener)
//        return this
//    }

    fun startScene(name: String): EngineActivity {
        registry.activeScene = registry.scenes[name]
        return this
    }

    fun addScene(name: String, scene: Scene): EngineActivity {
        registry.scenes.put(name, scene)
        scene.registry = registry
        return this
    }
}