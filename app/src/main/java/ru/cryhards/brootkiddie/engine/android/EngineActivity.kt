package ru.cryhards.brootkiddie.engine.android

import android.view.MotionEvent
import ru.cryhards.brootkiddie.engine.android.templates.FullScreenActivity
import ru.cryhards.brootkiddie.engine.environment.Scene

/**
 * Starts Engine workflow
 *
 * Created with love by luna_koly on 29.10.2017.
 */
open class EngineActivity : FullScreenActivity() {
    /**
     * Holds a reference to the main engine structure
     */
    protected lateinit var registry: EngineRegistry

    /**
     * Initialize engine with pre-defined surface
     */
    fun initSurface(value: EngineSurface): EngineActivity {
        registry = value.registry
        registry.activity = this
        return this
    }

    /**
     * Initialize engine with new surface
     */
    @Suppress("unused")
    fun initSurface(): EngineActivity {
        val surface = EngineSurface(this, null)
        setContentView(surface)
        registry = surface.registry
        registry.activity = this
        return this
    }


    private val onTouchListeners = ArrayList<(MotionEvent) -> Unit>()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        onTouchListeners.forEach { it.invoke(event) }
        registry.activeScene?.activeCamera?.onTouchEvent(event)
        return true
    }

    @Suppress("unused")
    fun addOnTouchListener(listener: (MotionEvent) -> Unit): EngineActivity {
        onTouchListeners.add(listener)
        return this
    }

    /**
     * Request engine to start the specified scene first
     */
    fun startScene(name: String): EngineActivity {
        registry.activeScene = registry.scenes[name]
        return this
    }

    /**
     * Register new scene
     */
    fun addScene(scene: Scene): EngineActivity {
        registry.scenes.put(scene.sceneName, scene)
        scene.registry = registry
        return this
    }
}