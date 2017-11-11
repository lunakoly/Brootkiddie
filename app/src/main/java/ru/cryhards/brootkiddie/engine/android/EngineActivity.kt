package ru.cryhards.brootkiddie.engine.android

import android.os.Bundle
import android.view.MotionEvent
import ru.cryhards.brootkiddie.engine.android.templates.FullScreenActivity

/**
 * Created with love by luna_koly on 29.10.2017.
 */
open class EngineActivity : FullScreenActivity() {
    lateinit var registry: EngineRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val surface = EngineSurface(this)
        setContentView(surface)

        registry = surface.registry
        registry.activity = this
    }

    private val onTouchListeners = ArrayList<(MotionEvent) -> Unit>()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        onTouchListeners.forEach { it.invoke(event) }
        return true
    }

    fun addOnTouchListener(listener: (MotionEvent) -> Unit) {
        onTouchListeners.add(listener)
    }
}