package ru.cryhards.brootkiddie.engine.android

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

/**
 * View that is used to draw the scene
 *
 * Created with love by luna_koly on 29.10.2017.
 */
class EngineSurface(context: Context, attributeSet: AttributeSet?) : GLSurfaceView(context, attributeSet) {
    /**
     * Holds a reference to the main engine structure
     */
    var registry: EngineRegistry

    init {
        // can't use 3, but the code is written for 3)
        setEGLContextClientVersion(2)
        val renderer = EngineRenderer(context)
        setRenderer(renderer)

        registry = renderer.registry
        registry.surface = this
    }
}