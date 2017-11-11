package ru.cryhards.brootkiddie.engine.android

import android.content.Context
import android.opengl.GLSurfaceView

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class EngineSurface(context: Context) : GLSurfaceView(context) {
    var registry: EngineRegistry

    init {
        setEGLContextClientVersion(3)
        val renderer = EngineRenderer(context)
        setRenderer(renderer)

        registry = renderer.registry
        registry.surface = this
    }
}