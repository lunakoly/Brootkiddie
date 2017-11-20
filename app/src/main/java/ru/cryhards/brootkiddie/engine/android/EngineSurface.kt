package ru.cryhards.brootkiddie.engine.android

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class EngineSurface(context: Context, attributeSet: AttributeSet?) : GLSurfaceView(context, attributeSet) {
    var registry: EngineRegistry

    init {
        setEGLContextClientVersion(2)
        val renderer = EngineRenderer(context)
        setRenderer(renderer)

        registry = renderer.registry
        registry.surface = this
    }
}